package game.control;

import java.util.*;

import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.executes.ChangeDirectionExecute;
import commands.executes.ChangeSpeedExecute;
import commands.executes.JumpExecute;
import commands.executes.KillExecute;
import commands.queries.*;
import feedback.NoFeedbackException;
import feedback.Result;
import field.*;
import game.Coord;

public class VacuumController implements GameControllerSocketListener {
    private Vacuum vacuum;
    private game.Map map;
    private Field expectedField;
    private GameControllerSocket socket;

    private List<AiCommand> commandQueue;

    public VacuumController(Vacuum vacuum, game.Map map, GameControllerSocket socket) {
        this.vacuum = vacuum;
        this.map = map;
        this.expectedField = null;
        this.socket = socket;

        this.commandQueue = new ArrayList<AiCommand>();
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        if (socket != sender) {
            return;
        }

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

        // TODO: Get rid of hardcoded speed? I don't really want to add ChangeSpeedQueries to
        // the command queue, because that would mean we have to recalculate the queue every time
        // something changes our speed
        vacuum.setSpeed(new Speed(Direction.UP, 1));

        Iterator<AiCommand> i = commandQueue.iterator();
        TurnChangeAgentCommandProbe probe = new TurnChangeAgentCommandProbe();
        while (i.hasNext()) {
            AiCommand current = i.next();
            vacuum.accept(current.command);
            expectedField = current.expectedField;
            i.remove();

            current.command.accept(probe);
            if (probe.changesTurn) {
                sender.sendEndTurn();
                break;
            }
        }
    }

    @Override
    public void socketClosed(GameControllerSocket sender) {
        if (socket != sender) {
            return;
        }

        // TODO: You cannot put any code that modifies the commandQueue in here (it will lead to concurrent modification
        // exceptions (this is due to us calling the socket's sendEndTurn() while iterating on the commands
    }

    public GameControllerSocket getSocket() {
        return socket;
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
        List<Field> cleanableFields = new ArrayList<Field>();

        for (Field f : map) {
            if (f.getFirstCleanableBuff() != null) {
                cleanableFields.add(f);
            }
        }

        if (cleanableFields.isEmpty()) {
            return null;
        }

        Field agentField = vacuum.getField();
        Field destination = cleanableFields.get(0);
        int minDistance = Coord.manhattan_distance(map.coordOf(agentField), map.coordOf(destination));

        for (Field f : cleanableFields) {
            int newDistance = Coord.manhattan_distance(map.coordOf(agentField), map.coordOf(f));

            if (newDistance < minDistance) {
                destination = f;
                minDistance = newDistance;
            }
        }

        return destination;
    }

    // Unoptimized, naive A* implementation
    private List<Field> getPath(Field start, Field goal) {
        // This is some ugly workaround, because Java doesn't support getting an arbitrary value with equals()
        // from HashSets
        SortedMap<Node, Node> open = new TreeMap<Node, Node>();
        SortedMap<Node, Node> closed = new TreeMap<Node, Node>();

        Node s = new Node(null, start, 0, 0, 0);
        open.put(s, s);

        while (!open.isEmpty()) {
            Node current = open.firstKey();
            open.remove(current);

            Collection<Field> successors = map.getNeighbours(current.field);
            removeEmptyFields(successors);

            Collection<Node> successorNodes = toNodes(successors, current);
            for (Node successor : successorNodes) {
                if (successor.field == goal) {
                    reconstructPath(successor);
                }

                successor.g = current.g + 1;
                successor.h = Coord.manhattan_distance(map.coordOf(successor.field), map.coordOf(goal));
                successor.f = successor.g + successor.h;

                if (open.get(successor) != null && open.get(successor).f <= successor.f) {
                    continue;
                } else if (closed.get(successor) != null && closed.get(successor).f <= successor.f) {
                    continue;
                } else {
                    // We remove the old value in the map, just in case Java would decide to optimize the
                    // put() when the in-map value is equal to successor
                    open.remove(successor);
                    open.put(successor, successor);
                }
            }

            closed.put(current, current);
        }

        return null;
    }

    private void populateCommandQueue(List<Field> path) {
        Field current = vacuum.getField();
        path.remove(current);

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
        public int f, g, h;

        public Node(Node parent, Field field, int f, int g, int h) {
            this.parent = parent;
            this.field = field;
            this.f = f;
            this.g = g;
            this.h = h;
        }

        public Node(Node parent, Field field) {
            this.parent = parent;
            this.field = field;
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

        // For out A* algorithm we only care about the equality of Fields contained in the Node.
        // For the rest of the values, we'll do manual comparisons
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return !(field != null ? !field.equals(node.field) : node.field != null);

        }

        @Override
        public int hashCode() {
            return field != null ? field.hashCode() : 0;
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

        while (goal.parent != null) {
            path.add(goal.field);
            goal = goal.parent;
        }

        Collections.reverse(path);
        return path;
    }
}
