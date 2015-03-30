package game.handle;

import agents.Agent;
import agents.Robot;

public class VacuumHandle extends AgentHandle {
    private int turnsRemaining;

    private VacuumHandle(Agent agent) {
        super(agent);
        turnsRemaining = 2;
    }

    //TODO: Return the correct Agent type
    public static VacuumHandle createVacuum() {
        return new VacuumHandle(new Robot());
    }

    @Override
    public void onTurnEnd() {
        turnsRemaining -= 1;

        if (turnsRemaining <= 0) {
            listener.onOutOfTime(this);
        }
    }

    @Override
    public long getTimeRemaining() {
        return 1;
    }

    @Override
    public void setTimeRemaining(long seconds) { }

    @Override
    public boolean isDisqualified() {
        return turnsRemaining <= 0;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}
