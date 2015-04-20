package game.handle;

import agents.Agent;
import agents.Robot;
import agents.Vacuum;

public class VacuumHandle extends AgentHandle {
    private int turnsRemaining;

    private VacuumHandle(Agent agent) {
        super(agent);
        turnsRemaining = 2;
    }

    public static VacuumHandle createVacuum() {
        return new VacuumHandle(new Vacuum());
    }

    @Override
    public void onTurnEnd() {
        //TODO: Why does the VacuumHandle get events when it shouldn't
        if (isDisqualified()) {
            return;
        }

        turnsRemaining -= 1;

        if (turnsRemaining <= 0) {
            listener.onOutOfTime(this);
        } else {
            super.onTurnEnd();
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
