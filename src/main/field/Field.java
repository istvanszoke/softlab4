package field;

import java.util.ArrayList;
import java.util.HashMap;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

public abstract class Field implements FieldElement {
    protected Agent agent;
    protected final int distanceFromGoal;
    protected final ArrayList<Buff> buffs;
    protected final HashMap<Direction, Field> neighbours;

    public Field(int distanceFromGoal) {
        buffs = new ArrayList<Buff>();
        neighbours = new HashMap<Direction, Field>();
        this.distanceFromGoal = distanceFromGoal;
    }

    public void addNeighbour(Direction direction, Field field) {
        neighbours.put(direction, field);
    }

    public void onEnter(Agent agent) {
        Inspector.call("Field.onEnter(Agent)");
        for (Buff b : buffs) {
            agent.accept(b);
        }

        agent.setField(this);
        this.agent = agent;
        Inspector.ret("Field.onEnter");
    }

    public void onExit() {
        Inspector.call("Field.onExit()");
        if (agent == null) {
            Inspector.ret("Field.onExit");
            return;
        }

        buffs.clear();

        agent.setField(null);
        this.agent = null;
        Inspector.ret("Field.onExit");
    }

    public int getDistanceFromGoal() {
        return distanceFromGoal;
    }

    public void placeBuff(Buff buff) {
        Inspector.call("Field.placeBuff(Buff)");
        buffs.add(buff);
        Inspector.ret("Field.placeBuff");
    }

    public Displacement getDisplacement(Speed speed) {
        Inspector.call("Field.getDisplacement(Speed):Displacement");
        Displacement tmp = new Displacement(this, searchGoal(speed));
        Inspector.ret("Field.getDisplacement");
        return tmp;
    }

    public boolean isEmpty() {
        Inspector.call("Field.isEmpty():boolean");
        Inspector.ret("Field.isEmpty");
        return agent == null;
    }

    protected Field searchGoal(Speed speed) {
        Inspector.call("Field.searchGoal(Speed):Field");
        if (speed.getMagnitude() == 0) {
            Inspector.ret("Field.searchGoal");
            return this;
        }

        Speed newSpeed = speed.clone();
        newSpeed.setMagnitude(newSpeed.getMagnitude() - 1);
        Field tmp = neighbours.get(speed.getDirection()).searchGoal(newSpeed);
        Inspector.ret("Field.searchGoal");
        return tmp;
    }
}
