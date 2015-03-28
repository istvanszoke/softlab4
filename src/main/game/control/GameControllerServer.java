package game.control;

import agents.Agent;
import commands.AgentCommand;
import game.Game;

import java.util.HashMap;

public class GameControllerServer {

    private class ControlSocket implements GameControllerSocket {

        GameControllerSocketListener clientToNofify = null;
        GameControllerServer server;

        private ControlSocket(GameControllerServer server) {
            if (server != null)
                this.server = server;
            else
                throw new NullPointerException();
        }

        @Override
        public boolean isOpen() {
            return server.isSocketOpen(this);
        }

        @Override
        public boolean sendEndTurn() {
            return server.receiveEndTurn(this);
        }

        @Override
        public boolean sendAgentCommand(AgentCommand command) {
            return server.receiveAgentCommand(this, command);
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
            if (clientToNofify != null)
                clientToNofify.socketOpened(this);
        }

        private void notifySocketClosed() {
            if (clientToNofify != null)
                clientToNofify.socketClosed(this);
        }
    }

    private Game servedGamed;
    private HashMap<ControlSocket, Agent> socketMapping
            = new HashMap<ControlSocket, Agent>();
    private HashMap<Agent, ControlSocket> agentMapping
            = new HashMap<Agent, ControlSocket>();
    private HashMap<GameControllerSocket, ControlSocket> globalToLocalMapping
            = new HashMap<GameControllerSocket, ControlSocket>();
    private final Object mappingOperationLock
            = new Object();

    public GameControllerServer(Game game) {
        if (game != null)
            servedGamed = game;
        else
            throw new NullPointerException();
    }

    private boolean isSocketOpen(GameControllerSocket socket) {
        ControlSocket controlSocket = globalToLocalMapping.get(socket);
        if (controlSocket == null)
            return false;
        Agent agent = socketMapping.get(controlSocket);

        return agent != null && agent == servedGamed.getCurrentAgent();
    }

    private boolean receiveEndTurn(GameControllerSocket client) {
        if (isSocketOpen(client)) {
            servedGamed.onAgentChange();
            return true;
        }
        return false;
    }

    private boolean receiveAgentCommand(GameControllerSocket socket, AgentCommand command) {
        ControlSocket controlSocket = globalToLocalMapping.get(socket);
        if (controlSocket == null)
            return false;
        Agent agent = socketMapping.get(controlSocket);
        if (agent == null) {
            return false;
        } else if (agent == servedGamed.getCurrentAgent()) {
            servedGamed.getCurrentAgent().accept(command);
            return true;
        } /* else if proszivo robot then... */
        return false;
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
}
