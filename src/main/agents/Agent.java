package agents;

import field.Direction;
import field.Field;
import inspector.Inspector;

public abstract class Agent implements AgentElement {
    protected Speed speed;
    protected Field field;
    protected boolean isDead;
    protected int currentLap;

    public Agent() {
        Inspector.call("Agent.Agent()");
        speed = new Speed(Direction.UP, 0);
        isDead = false;
        currentLap = 1;
        Inspector.ret("Agent.Agent");
    }

    public Speed getSpeed() {
        Inspector.call("Agent.getSpeed():Speed");
        Speed tmp = speed.clone();
        Inspector.ret("Agent.getSpeed");
        return tmp;
    }

    public void setSpeed(Speed speed) {
        Inspector.call("Agent.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("Agent.setSpeed");
    }

    public Field getField() {
        Inspector.call("Agent.getField():Field");
        Inspector.ret("Agent.getField");
        return field;
    }

    public void setField(Field field) {
        Inspector.call("Agent.setField(Field)");
        Inspector.ret("Agent.setField");
        this.field = field;
    }

    public boolean isDead() {
        Inspector.call("Agent.isDead():boolean");
        Inspector.ret("Agent.isDead");
        return isDead;
    }

    public void kill() {
        Inspector.call("Agent.kill()");
        this.isDead = true;
        Inspector.ret("Agent.kill");
    }

    public int getLap()
    {
        Inspector.call("Agent.getLap()");
        Inspector.ret("Agent.getLap");
        return currentLap;
    }

    public void incrementLap() {
        Inspector.call("Agent.incrementLap()");
        this.currentLap++;
        Inspector.ret("Agent.incrementLap");
    }
}
