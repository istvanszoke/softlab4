package field;

import java.util.*;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import buff.BuffListener;
import commands.FieldCommand;

public abstract class Field implements FieldElement, BuffListener {
    public static final Field GRAVEYARD = new EmptyFieldCell(-1);

    protected final int distanceFromGoal;
    protected final List<Buff> buffs;
    protected final Map<Direction, Field> neighbours;
    protected Agent agent;
    private int fieldId;
    private static int instanceCount = 0;

    public static void resetInstanceCount() {
        instanceCount = 0;
    }

    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
        ++instanceCount;
        fieldId = instanceCount;
    }

    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }

    public Field getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }

    public boolean onEnter(Agent agent) {
        if (agent == this.agent) {
            return false;
        }

        if (agent != null && this.agent != null) {
            if (agent.onCauseCollision(this.agent)) {
                agent = this.agent.collide(agent);
            } else {
                return false;
            }
        }

        if (agent == null) {
            return false;
        }

        acceptBuffs(agent);

        agent.setField(this);
        this.agent = agent;

        return true;
    }

    public void onExit() {
        if (agent == null) {
            return;
        }

        this.agent = null;
    }

    public int getDistanceFromGoal() {
        return distanceFromGoal;
    }

    public void placeBuff(Buff buff) {
        buff.subscribe(this);
        buffs.add(buff);
    }

    public Buff getFirstCleanableBuff() {
        for (Buff buff : buffs) {
            if (buff.getCleanable()) {
                return buff;
            }
        }
        return null;
    }

    public Displacement getDisplacement(Speed speed) {
        SearchResult result = searchGoal(speed, 0);
        return new Displacement(this, result.field, result.passedFinishLine);
    }

    public Displacement getUnconditionalDisplacement(Speed speed) {
        SearchResult result = unconditionalSearchGoal(speed, 0);
        return new Displacement(this, result.field, result.passedFinishLine);
    }

    public boolean isEmpty() {
        return agent == null;
    }

    @Override
    public void onRemove(Buff buff) {
        buff.unsubscribe(this);
        buffs.remove(buff);
    }

    protected SearchResult searchGoal(Speed speed, int depth) {
        if (speed.getMagnitude() == 0) {
            return new SearchResult(this, false);
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed, depth + 1);
    }

    protected SearchResult unconditionalSearchGoal(Speed speed, int depth) {
        return searchGoal(speed, depth);
    }

    protected void acceptBuffs(FieldCommand command) {
        if (buffs.isEmpty()) {
            return;
        }

        List<Buff> buffsCopy = new ArrayList<Buff>(buffs.size());
        for (Buff b : buffs) {
            buffsCopy.add(b);
        }

        Collections.copy(buffsCopy, buffs);
        for (Buff b : buffsCopy) {
            command.accept(b);
        }
    }

    protected void acceptBuffs(Agent agent) {
        if (buffs.isEmpty()) {
            return;
        }

        List<Buff> buffsCopy = new ArrayList<Buff>(buffs.size());
        for (Buff b : buffs) {
            buffsCopy.add(b);
        }

        for (Buff b : buffsCopy) {
            agent.accept(b);
        }
    }

    public int getFieldId() {
        return fieldId;
    }


    public Agent getAgent() { return agent; }

    public List<Buff> getBuffs() {
        return Collections.unmodifiableList(buffs);
    }

    @Override
    public String toString() {
        return "Field:" + fieldId;
    }

    protected static class SearchResult {
        public boolean passedFinishLine;
        public Field field;

        public SearchResult(Field field, boolean passedFinishLine) {
            this.field = field;
            this.passedFinishLine = passedFinishLine;
        }
    }
}
