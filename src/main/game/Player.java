package game;

import agents.Agent;

public class Player {
    private AgentController controller = null;
    private int timeRemaining;

    public static Player createHuman(Game game, int timeInSec) {
        Player instance = new Player();
        instance.controller = new HumanController(game);
        instance.timeRemaining = timeInSec * 1000;
        return instance;
    }

    public Agent getAgent() {
        return null;
    }

    public AgentController getController() {
        return controller;
    }

    public void activate() {
        if (isFinished()) {
            return;
        }
    }

    public void setTimeRemaining(int milliseconds) {
        timeRemaining = milliseconds;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }
}
