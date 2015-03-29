package util;

import buff.Buff;
import buff.BuffListener;
import game.handle.AgentHandle;
import game.handle.HandleListener;

public final class DevNull implements HandleListener, BuffListener {
    public static final DevNull SINK = new DevNull();

    @Override
    public void onRegularTurn(AgentHandle handle) { }

    @Override
    public void onOutOfTime(AgentHandle handle) { }

    @Override
    public void onAgentDeath(AgentHandle handle) { }

    @Override
    public void onRemove(Buff buff) { }

    private DevNull() { }
}
