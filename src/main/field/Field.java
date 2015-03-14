package field;

import agents.Agent;
import agents.Speed;
import buff.Buff;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Field implements FieldElement {
    protected Agent agent;
    protected int distanceFromGoal;
    protected ArrayList<Buff> buffs = new ArrayList<Buff>();
    protected HashMap<Direction, Field> neighbours = new HashMap<Direction, Field>();

    public Field(int distanceFromGoal) {
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

    protected Field searchGoal(Speed speed) {
        if (speed.getMagnitude() == 0) {
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        return neighbours.get(speed.getDirection()).searchGoal(newSpeed);
    }
}
