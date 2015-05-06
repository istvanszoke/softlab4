package game.control;

import java.util.*;

import agents.AgentVisitor;
import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.executes.ChangeDirectionExecute;
import commands.executes.ChangeSpeedExecute;
import commands.executes.JumpExecute;
import commands.executes.KillExecute;
import commands.queries.*;
import feedback.Logger;
import feedback.NoFeedbackException;
import feedback.Result;
import field.*;
import game.Coord;

public class VacuumController implements GameControllerSocketListener {
    private Vacuum vacuum;
    private game.Map map;
    private Field expectedField;

    private List<AiCommand> commandQueue;

    public VacuumController(Vacuum vacuum, game.Map map) {
        this.vacuum = vacuum;
        this.map = map;
        this.expectedField = null;

        this.commandQueue = new ArrayList<AiCommand>();
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        if (vacuum.isDead()) {
            return;
        }

        if (commandQueue.isEmpty()) {
            calculateCommands();
        } else if (vacuum.getField() != expectedField) {
            // If there was an unexpected collision then we'll recalculate our path
            commandQueue.clear();
            calculateCommands();
        }

        if (commandQueue.isEmpty()) {
            sender.sendEndTurn();
        }

        // TODO: Get rid of hardcoded speed? I don't really want to add ChangeSpeedQueries to
        // the command queue, because that would mean we have to recalculate the queue every time
        // something changes our speed
        vacuum.setSpeed(new Speed(Direction.UP, 1));

        Iterator<AiCommand> i = commandQueue.iterator();
        TurnChangeAgentCommandProbe probe = new TurnChangeAgentCommandProbe();
        while (i.hasNext()) {
            AiCommand current = i.next();
            sender.sendAgentCommand(current.command);
            expectedField = current.expectedField;
            i.remove();

            current.command.accept(probe);
            Logger.log(current.command.getResult());
            if (probe.changesTurn) {
                sender.sendEndTurn();
                break;
            }
        }
    }

    @Override
    public void socketClosed(GameControllerSocket sender) {
        // TODO: You cannot put any code that modifies the commandQueue in here (it will lead to concurrent modification
        // exceptions (this is due to us calling the socket's sendEndTurn() while iterating on the commands
    }

    public Vacuum getVacuum() {
        return vacuum;
    }

    private void calculateCommands() {
        Field destination = getDestination();
        if (destination == null) {
            return;
        }

        List<Field> path = getPath(vacuum.getField(), destination);
        if (path == null) {
            return;
        }

        populateCommandQueue(path);
    }

    private Field getDestination() {
        if (vacuum.getField().getFirstCleanableBuff() != null) {
            return vacuum.getField();
        }

        List<Field> cleanableFields = new ArrayList<Field>();

        for (Field f : map) {
            if (f.getFirstCleanableBuff() != null) {
                cleanableFields.add(f);
            }
        }

        if (cleanableFields.isEmpty()) {
            return null;
        }

        VacuumProbe probe = new VacuumProbe();
        Field agentField = vacuum.getField();
        Field destination = cleanableFields.get(0);
        int minDistance = Coord.manhattan_distance(map.coordOf(agentField), map.coordOf(destination));

        for (Field f : cleanableFields) {
            int newDistance = Coord.manhattan_distance(map.coordOf(agentField), map.coordOf(f));

            probe.isVacuum = false;
            if (f.getAgent() != null) {
                f.getAgent().accept(probe);
            }

            if (newDistance <= minDistance && !probe.isVacuum) {
                destination = f;
                minDistance = newDistance;
            }
        }

        return destination;
    }

    private List<Field> getPath(final Field start, final Field goal) {
        if (map.coordOf(start).equals(map.coordOf(goal))) {
            return new ArrayList<Field>() {{ add(start); }};
        }

        Map<Coord, Node> open = new HashMap<Coord, Node>();
        Map<Coord, Node> closed = new HashMap<Coord, Node>();

        Node startNode = new Node(null, start, 0, 0, 0);
        open.put(startNode.coord, startNode);

        while (!open.isEmpty()) {
            Node current = popLowest(open);

            Collection<Field> successors = map.getNeighbours(current.field);
            removeEmptyFields(successors);

            Collection<Node> successorNodes = toNodes(successors, current);
            for (Node successor : successorNodes) {
                if (successor.field == goal) {
                    return reconstructPath(successor);
                }

                successor.g = current.g + 1;
                successor.h = Coord.manhattan_distance(map.coordOf(successor.field), map.coordOf(goal));
                successor.f = successor.g + successor.h;


                Node openNode = open.get(successor.coord);
                Node closedNode = closed.get(successor.coord);
                if (openNode != null && openNode.f <= successor.f) {
                    continue;
                } else if (closedNode != null && closedNode.f <= successor.f) {
                    continue;
                }

                open.remove(successor.coord);
                open.put(successor.coord, successor);
            }
            refreshNode(closed, current);
        }

        return null;
    }

    private void populateCommandQueue(List<Field> path) {
        Field current = path.get(0);
        if (path.size() == 1) {
            commandQueue.add(new AiCommand(new CleanFieldQuery(), current));
            commandQueue.add(new AiCommand(new CleanFieldQuery(), current));
            return;
        }
        path.remove(0);

        boolean directionSet;
        for (Field next : path) {
            directionSet = true;
            if (current.getNeighbour(Direction.UP) == next) {
                commandQueue.add(new AiCommand(new ChangeDirectionQuery(Direction.UP), current));
            } else if (current.getNeighbour(Direction.DOWN) == next) {
                commandQueue.add(new AiCommand(new ChangeDirectionQuery(Direction.DOWN), current));
            } else if (current.getNeighbour(Direction.LEFT) == next) {
                commandQueue.add(new AiCommand(new ChangeDirectionQuery(Direction.LEFT), current));
            } else if (current.getNeighbour(Direction.RIGHT) == next) {
                commandQueue.add(new AiCommand(new ChangeDirectionQuery(Direction.RIGHT), current));
            } else {
                directionSet = false;
            }

            if (directionSet) {
                commandQueue.add(new AiCommand(new JumpQuery(), next));
            }

            current = next;
        }

        commandQueue.add(new AiCommand(new CleanFieldQuery(), current));
        commandQueue.add(new AiCommand(new CleanFieldQuery(), current));
    }

    private class AiCommand {
        public AgentCommand command;
        public Field expectedField;

        public AiCommand(AgentCommand command, Field expectedField) {
            this.command = command;
            this.expectedField = expectedField;
        }
    }

    private class Node implements Comparable<Node> {
        public Node parent;
        public Field field;
        public Coord coord;
        public int f, g, h;

        public Node(Node parent, Field field, int f, int g, int h) {
            this.parent = parent;
            this.field = field;
            this.coord = map.coordOf(field);
            this.f = f;
            this.g = g;
            this.h = h;
        }

        public Node(Node parent, Field field) {
            this.parent = parent;
            this.field = field;
            this.coord = map.coordOf(field);
            this.f = 0;
            this.g = 0;
            this.h = 0;
        }

        @Override
        public int compareTo(Node node) {
            if (f < node.f) {
                return -1;
            } else if (f == node.f) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return !(coord != null ? !coord.equals(node.coord) : node.coord != null);

        }

        @Override
        public int hashCode() {
            return coord != null ? coord.hashCode() : 0;
        }
    }

    private class EmptyFieldProbe implements FieldVisitor {
        public boolean isEmpty = false;

        @Override
        public void visit(FieldCell element) {
            isEmpty = false;
        }

        @Override
        public void visit(EmptyFieldCell element) {
            isEmpty = true;
        }

        @Override
        public void visit(FinishLineFieldCell element) {
            isEmpty = false;
        }

        @Override
        public Result getResult() throws NoFeedbackException {
            return null;
        }
    }

    private class VacuumProbe implements AgentVisitor {
        public boolean isVacuum = false;
        @Override
        public void visit(Robot element) {
            isVacuum = false;
        }

        @Override
        public void visit(Vacuum element) {
            isVacuum = true;
        }

        @Override
        public Result getResult() throws NoFeedbackException {
            return null;
        }
    }

    private class TurnChangeAgentCommandProbe implements AgentCommandVisitor {
        public boolean changesTurn = false;

        @Override
        public void visit(ChangeDirectionQuery command) {
            changesTurn = false;
        }

        @Override
        public void visit(ChangeDirectionExecute command) {
            changesTurn = false;
        }

        @Override
        public void visit(ChangeSpeedQuery command) {
            changesTurn = false;
        }

        @Override
        public void visit(ChangeSpeedExecute command) {
            changesTurn = false;
        }

        @Override
        public void visit(JumpQuery command) {
            changesTurn = true;
        }

        @Override
        public void visit(JumpExecute command) {
            changesTurn = true;
        }

        @Override
        public void visit(KillExecute command) {
            changesTurn = true;
        }

        @Override
        public void visit(UseStickyQuery command) {
            changesTurn = false;
        }

        @Override
        public void visit(UseOilQuery command) {
            changesTurn = false;
        }

        @Override
        public void visit(CleanFieldQuery command) {
            changesTurn = true;
        }
    }

    private void removeEmptyFields(Collection<Field> fields) {
        Iterator<Field> i = fields.iterator();
        EmptyFieldProbe probe = new EmptyFieldProbe();
        while (i.hasNext()) {
            i.next().accept(probe);
            if (probe.isEmpty) {
                i.remove();
            }
        }
    }

    private Collection<Node> toNodes(Collection<Field> fields, Node parent) {
        List<Node> nodes = new ArrayList<Node>();
        for (Field f : fields) {
            nodes.add(new Node(parent, f));
        }
        return nodes;
    }

    private List<Field> reconstructPath(Node goal) {
        List<Field> path = new ArrayList<Field>();

        while (goal != null) {
            path.add(goal.field);
            goal = goal.parent;
        }

        Collections.reverse(path);
        for (Field f : path) {
            System.out.println(map.coordOf(f));
        }

        return path;
    }

    private Node popLowest(Map<Coord, Node> c) {
        Iterator<Map.Entry<Coord, Node>> i = c.entrySet().iterator();
        Node ret = i.next().getValue();
        int minF = ret.f;

        while (i.hasNext()) {
            Node current = i.next().getValue();
            if (current.f < minF) {
                ret = current;
                minF = current.f;
            }
        }

        c.remove(ret.coord);

        return ret;
    }

    private void refreshNode(Map<Coord, Node> c, Node n) {
        Node inMap = c.get(n.coord);

        if (inMap == null) {
            c.put(n.coord, n);
        } else if (inMap.f > n.f) {
            inMap.f = n.f;
        }
    }
}
