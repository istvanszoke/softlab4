package game.control;

import agents.Agent;
import commands.AgentCommand;

/**
 * Created by nyari on 2015.03.27..
 */
public interface GameControllerSocket
{
    boolean isOpen();
    void sendEndTurn();
    void sendAgentCommand(AgentCommand command);
    void enableStateNotification(GameControllerClient client);
    void disableStateNotification();
}
