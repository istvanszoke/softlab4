package game;

import agents.Agent;
import agents.Robot;

public class Player {
    private Agent agent;
    private int timeRemaining;

    public static Player createRobot(int timeInSec) {
        Player instance = new Player();
        instance.agent = new Robot();
        instance.timeRemaining = timeInSec * 1000;
        return instance;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setTimeRemaining(int milliseconds) {
        timeRemaining = milliseconds;

        if (isFinished()) {
            agent.timeOut();
        }
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }
}
