package game.control;

import commands.AgentCommand;

public interface GameControllerSocket {
    boolean isOpen();

    boolean sendEndTurn();

    boolean sendAgentCommand(AgentCommand command);

    void enableStateNotification(GameControllerSocketListener client);

    void disableStateNotification();
}
