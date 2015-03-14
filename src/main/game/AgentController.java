package game;

import agents.Agent;

import java.awt.event.KeyAdapter;

public abstract class AgentController extends KeyAdapter {
    protected final Agent agent;
    protected boolean isActive;

    public AgentController(Agent agent) {
        this.agent = agent;
    }

    public synchronized void activate() {
        isActive = true;

        while (isActive() && !Thread.currentThread().isInterrupted()) {}
    }

    public synchronized void deactivate() {
        isActive = false;
    }

    public synchronized boolean isActive() {
        return isActive;
    }

    public Agent getAgent() {
        return agent;
    }
}