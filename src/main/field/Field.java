package field;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import buff.BuffListener;

public abstract class Field implements FieldElement, BuffListener, Serializable {
    public static final Field GRAVEYARD = new EmptyFieldCell(-1);

    protected final int distanceFromGoal;
    protected final List<Buff> buffs;
    protected final List<Buff> buffsToRemove;
    protected final Map<Direction, Field> neighbours;
    protected Agent agent;
    private int fieldId;
    private static int instanceCount = 0;

    public static void resetInstanceCount() {
        instanceCount = 0;
    }

    public static void writeStaticParams(ObjectOutputStream oos) throws IOException {
        Integer wrapOutput = instanceCount;
        oos.writeObject(wrapOutput);
    }

    public static void readStaticParams(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Integer wrapInput = (Integer)ois.readObject();
        instanceCount = wrapInput;
    }

    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        buffsToRemove = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
        ++instanceCount;
        fieldId = instanceCount;
    }

    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }

    public void onEnter(Agent agent) {
        if (agent != null && this.agent != null && agent != this.agent) {
            if (agent.onCauseCollision(this.agent)) {
                agent = this.agent.collide(agent);
            }
        }

        if (agent == null) {
            return;
        }

        for (Buff b : buffs) {
            agent.accept(b);
        }
        removeBuffs();

        agent.setField(this);
        this.agent = agent;
    }

    public void onExit() {
        if (agent == null) {
            return;
        }

        removeBuffs();

        agent.setField(null);
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
        return new Displacement(this, searchGoal(speed));
    }

    public boolean isEmpty() {
        return agent == null;
    }

    @Override
    public void onRemove(Buff buff) {
        buffsToRemove.add(buff);
    }

    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
    }

    public int getFieldId() {
        return fieldId;
    }

    @Override
    public String toString() {
        return "Field:" + fieldId;
    }

    protected void removeBuffs() {
        for (Buff b : buffsToRemove) {
            b.unsubscribe(this);
        }

        buffs.removeAll(buffsToRemove);
        buffsToRemove.clear();
    }
}
