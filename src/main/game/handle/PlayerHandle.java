package game.handle;

import agents.Agent;
import agents.Robot;

public class PlayerHandle extends AgentHandle {
    private long timeRemaining;

    private PlayerHandle(Agent agent, long timeRemaining) {
        super(agent);
        this.timeRemaining = timeRemaining * 1000;
    }

    public static PlayerHandle createRobot(int roundTime) {
        return new PlayerHandle(new Robot(), roundTime);
    }

    public synchronized long getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public synchronized void setTimeRemaining(long milliseconds) {
        timeRemaining = milliseconds;

        if (timeRemaining <= 0) {
            listener.onOutOfTime(this);
        }
    }

    @Override
    public boolean isDisqualified() {
        return getTimeRemaining() <= 0 || agent.isDead();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }
}
