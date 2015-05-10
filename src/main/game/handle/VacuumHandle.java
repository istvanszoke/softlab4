package game.handle;

import agents.Agent;
import agents.Robot;
import agents.Vacuum;

public class VacuumHandle extends AgentHandle {

    private static final long serialVersionUID = -9092309162816953432L;

    public VacuumHandle(Vacuum agent) {
        super(agent);
    }

    public static VacuumHandle createVacuum() {
        return new VacuumHandle(new Vacuum());
    }

    @Override
    public long getTimeRemaining() {
        return 1;
    }

    @Override
    public void setTimeRemaining(long seconds) { }

    @Override
    public boolean isDisqualified() {
        return agent.isDead();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}
