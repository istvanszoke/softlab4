package game;

import agents.Agent;

public class Player {
    private final AgentController controller;
    private int timeRemaining;

    private Player(AgentController agentController, int timeInSec) {
        controller = agentController;
        timeRemaining = timeInSec * 1000;
    }

    public static Player createHuman(Agent agent, int timeInSec) {
        return new Player(new HumanController(agent), timeInSec);
    }

    public Agent getAgent() {
        return controller.getAgent();
    }

    public AgentController getController() {
        return controller;
    }

    public void activate() {
        if (isFinished()) {
            return;
        }

        controller.activate();
    }

    public void deactivate() {
        controller.deactivate();
    }

    public void setTimeRemaining(int milliseconds) {
        timeRemaining -= milliseconds;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }
}
