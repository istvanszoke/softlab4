package game.handle;

import agents.Agent;
import util.DevNull;

public abstract class AgentHandle {
    protected final Agent agent;
    protected HandleListener listener;

    protected AgentHandle(Agent agent) {
        this.agent = agent;
        this.listener = DevNull.SINK;
    }

    public Agent getAgent() {
        return agent;
    }

    public void register(HandleListener listener) {
        this.listener = listener;
    }

    public void deregister(HandleListener listener) {
        this.listener = DevNull.SINK;
    }

    public void onTurnEnd() {
        if (agent.isDead()) {
            listener.onAgentDeath(this);
            return;
        }

        listener.onRegularTurn(this);
    }

    public abstract long getTimeRemaining();

    public abstract void setTimeRemaining(long milliseconds);

    public abstract boolean isDisqualified();

    public abstract boolean isPlayer();
}
