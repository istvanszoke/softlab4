package field;

import java.util.ArrayList;
import java.util.HashMap;

import agents.Agent;
import agents.Speed;
import buff.Buff;

public abstract class Field implements FieldElement {
    protected Agent agent;
    protected int distanceFromGoal;
    protected ArrayList<Buff> buffs;
    protected HashMap<Direction, Field> neighbours;

    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
    }

    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }

    public void onEnter(Agent agent) {
        for (Buff b : buffs) {
            agent.accept(b);
        }

        agent.setField(this);
        this.agent = agent;
    }

    public void onExit() {
        if (agent == null) {
            return;
        }

        buffs.clear();

        agent.setField(null);
        this.agent = null;
    }

    public int getDistanceFromGoal() {
        return distanceFromGoal;
    }

    public void placeBuff(Buff buff) {
        buffs.add(buff);
    }

    public Displacement getDisplacement(Speed speed) {
        return new Displacement(this, searchGoal(speed));
    }

    public boolean isEmpty() {
        return agent == null;
    }

    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
    }
}
