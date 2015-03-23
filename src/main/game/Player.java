package game;

import agents.Agent;
import agents.Robot;
import inspector.Inspector;

public class Player {
    private Agent agent;
    private int timeRemaining;

    public static Player createRobot(int timeInSec) {
        Inspector.call("Player.createRobot(int timeInSec):Player");

        Player instance = new Player();
        instance.agent = new Robot();
        instance.timeRemaining = timeInSec * 1000;

        Inspector.ret("Player.createRobot");
        return instance;
    }

    public Agent getAgent() {
        Inspector.call("Player.getAgent():Agent");
        Inspector.ret("Player.getAgent");
        return agent;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int milliseconds) {
        timeRemaining = milliseconds;
    }

    public boolean isOutOfTime() {
        return timeRemaining <= 0;
    }
}
