package input;

import agents.Agent;

public abstract class Controller {
    public abstract void changeDirection();
    public abstract void changeSpeed();
    public abstract void useSticky();
    public abstract void useOil();
    public abstract void jump();

    public abstract boolean isActive();
    public abstract void setActive(boolean active);

    private boolean isActive;
    private Agent target;
}
