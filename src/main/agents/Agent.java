package agents;

import java.io.Serializable;

import field.Direction;
import field.Field;

public abstract class Agent implements AgentElement, Serializable {
    private static final long serialVersionUID = -9123063851218789245L;

    protected Speed speed;
    protected Field field;
    protected boolean isDead;
    protected int currentLap;

    public Agent() {
        speed = new Speed(Direction.UP, 0);
        isDead = false;
        currentLap = 1;
    }

    public Speed getSpeed() {
        return speed.clone();
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        this.isDead = true;
        field.onExit();
        setField(Field.GRAVEYARD);
    }

    public int getLap() {
        return currentLap;
    }

    public void incrementLap() {
        this.currentLap++;
    }

    public abstract boolean onCauseCollision(Agent agent);

    public abstract Agent collide(Agent agent);
}
