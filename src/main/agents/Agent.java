package agents;

import field.Direction;
import field.Field;

public abstract class Agent implements AgentElement {
    protected Speed speed;
    protected Field field;
    protected boolean isDead;
    protected boolean isOutOfTime;
    protected int currentLap;

    public Agent() {
        speed = new Speed(Direction.UP, 0);
        isDead = isOutOfTime = false;
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
    }

    public synchronized boolean isOutOfTime() {
        return isOutOfTime;
    }

    public synchronized void timeOut() {
        this.isOutOfTime = true;
    }

    public int getLap() {
        return currentLap;
    }

    public void incrementLap() {
        this.currentLap++;
    }
}
