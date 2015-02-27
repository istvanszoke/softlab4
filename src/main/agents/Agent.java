package agents;

import field.Field;

public abstract class Agent implements AgentElement {
    protected Speed speed;
    protected Field field;
    protected boolean isDead;
    protected boolean isOutOfTime;
    protected int currentLap;

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


    public boolean isOutOfTime() {
        return isOutOfTime;
    }

    public void timeOut() {
        this.isOutOfTime = true;
    }

    public int getLap() {
        return currentLap;
    }

    public void incrementLap() {
        this.currentLap++;
    }
}
