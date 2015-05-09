package game;

import java.util.List;

import agents.Agent;
import game.handle.AgentHandle;

public interface GameListener {
    void onGameFinished(List<AgentHandle> playerList);
    void onAgentChange(Agent nextAgent);
}
