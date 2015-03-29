package game.handle;

public class HandleSink implements HandleListener {
    public static final HandleSink GLOBAL = new HandleSink();

    @Override
    public void onRegularTurn(AgentHandle handle) { }

    @Override
    public void onOutOfTime(AgentHandle handle) { }

    @Override
    public void onAgentDeath(AgentHandle handle) { }
}
