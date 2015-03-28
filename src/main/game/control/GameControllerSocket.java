package game.control;

import commands.AgentCommand;

/**
 * Created by nyari on 2015.03.27..
 */
public interface GameControllerSocket {
    boolean isOpen();

    boolean sendEndTurn();

    boolean sendAgentCommand(AgentCommand command);

    void enableStateNotification(GameControllerSocketListener client);

    void disableStateNotification();
}
