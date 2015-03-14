package game;

import agents.Agent;

import java.awt.event.KeyAdapter;

public abstract class AgentController extends KeyAdapter {
    protected final Game game;

    public AgentController(Game game) {
        this.game = game;
    }
}