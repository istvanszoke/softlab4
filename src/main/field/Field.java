package field;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import buff.BuffListener;
import commands.FieldCommand;

public abstract class Field implements FieldElement, BuffListener, Serializable {
    public static final Field GRAVEYARD = new EmptyFieldCell(-1);
    private static final long serialVersionUID = 6678011536302536430L;

    protected final int distanceFromGoal;
    protected final List<Buff> buffs;
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
        instanceCount = (Integer)ois.readObject();;
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
        return new Displacement(this, searchGoal(speed));
    }

    public boolean isEmpty() {
        return agent == null;
    }

    @Override
    public void onRemove(Buff buff) {
        buff.unsubscribe(this);
        buffs.remove(buff);
    }

    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
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
}
