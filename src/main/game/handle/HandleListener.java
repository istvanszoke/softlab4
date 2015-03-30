package game.handle;

public interface HandleListener {
    void onRegularTurn(AgentHandle handle);

    void onOutOfTime(AgentHandle handle);

    void onAgentDeath(AgentHandle handle);
}
