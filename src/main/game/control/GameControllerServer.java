package game.control;

import java.util.HashMap;
import java.util.Map;

import agents.Agent;
import commands.AgentCommand;

public class GameControllerServer {

    private final Object mappingOperationLock;
    private GameControllerServerListener listener;
    private Map<ControlSocket, Agent> socketMapping;
    private Map<Agent, ControlSocket> agentMapping;
    private Map<GameControllerSocket, ControlSocket> globalToLocalMapping;

    public GameControllerServer(GameControllerServerListener listener) {
        if (listener != null) {
            this.listener = listener;
        } else {
            throw new NullPointerException();
        }

        mappingOperationLock = new Object();
        socketMapping = new HashMap<ControlSocket, Agent>();
        agentMapping = new HashMap<Agent, ControlSocket>();
        globalToLocalMapping = new HashMap<GameControllerSocket, ControlSocket>();
    }

    private boolean receiveEndTurn(GameControllerSocket client) {
        ControlSocket controlSocket = globalToLocalMapping.get(client);

        if (controlSocket == null) {
            return false;
        }

        Agent agent = socketMapping.get(controlSocket);

        if (agent == null) {
            return false;
        }

        listener.onAgentChange(agent);
        return true;
    }

    private boolean receiveAgentCommand(GameControllerSocket socket, AgentCommand command) {
        ControlSocket controlSocket = globalToLocalMapping.get(socket);
        if (controlSocket == null) {
            return false;
        }
        Agent agent = socketMapping.get(controlSocket);
        if (agent == null) {
            return false;
        }
        agent.accept(command);
        return true;
    }

    public GameControllerSocket createSocketForAgent(Agent agent) {
        synchronized (mappingOperationLock) {
            if (!agentMapping.containsKey(agent)) {
                ControlSocket newSocket = new ControlSocket(this);
                agentMapping.put(agent, newSocket);
                socketMapping.put(newSocket, agent);
                globalToLocalMapping.put(newSocket, newSocket);
                return newSocket;
            } else {
                return null;
            }
        }
    }

    public void removeAgent(Agent agent) {
        synchronized (mappingOperationLock) {
            if (agentMapping.containsKey(agent)) {
                ControlSocket toRemove = agentMapping.get(agent);
                agentMapping.remove(agent);
                socketMapping.remove(toRemove);
                globalToLocalMapping.remove(toRemove);
            }
        }
    }

    public void notifyControllerSocketOpened(Agent agent) {
        if (agentMapping.containsKey(agent)) {
            agentMapping.get(agent).notifySocketOpened();
        }
    }

    public void notifyControllerSocketClosed(Agent agent) {
        if (agentMapping.containsKey(agent)) {
            agentMapping.get(agent).notifySocketClosed();
        }
    }

    private class ControlSocket implements GameControllerSocket {

        GameControllerSocketListener clientToNofify = null;
        GameControllerServer server;
        boolean isOpened = false;

        private ControlSocket(GameControllerServer server) {
            if (server != null) {
                this.server = server;
            } else {
                throw new NullPointerException();
            }
        }

        @Override
        public boolean isOpen() { return isOpened; }

        @Override
        public boolean sendEndTurn() {
            return isOpened && server.receiveEndTurn(this);
        }

        @Override
        public boolean sendAgentCommand(AgentCommand command) {
            return isOpened && server.receiveAgentCommand(this, command);
        }

        @Override
        public void enableStateNotification(GameControllerSocketListener client) {
            clientToNofify = client;
        }

        @Override
        public void disableStateNotification() {
            clientToNofify = null;
        }

        private void notifySocketOpened() {
            if (clientToNofify != null) {
                clientToNofify.socketOpened(this);
            }
            isOpened = true;
        }

        private void notifySocketClosed() {
            if (clientToNofify != null) {
                clientToNofify.socketClosed(this);
            }
            isOpened = false;
        }
    }
}
