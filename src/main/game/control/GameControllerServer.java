package game.control;

import agents.Agent;
import commands.AgentCommand;
import game.Game;

import java.util.HashMap;

/**
 * Created by nyari on 2015.03.27..
 */
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
        public void sendEndTurn() {
            server.receiveEndTurn(this);
        }

        @Override
        public void sendAgentCommand(AgentCommand command) {
            server.receiveAgentCommand(this, command);
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
    private Object mappingOperationLock
            = new Object();

    public GameControllerServer(Game game) {
        if (game != null)
            servedGamed = game;
        else
            throw new NullPointerException();
    }

    private boolean isSocketOpen(GameControllerSocket socket) {
        Agent agent = socketMapping.get(socket);
        if (agent == null)
            return false;
        if (agent == servedGamed.getCurrentAgent() /*||
            if among the sucker robots*/)
            return true;

        return false;
    }

    private void receiveEndTurn(GameControllerSocket client) {
        if (isSocketOpen(client)) {
            servedGamed.onAgentChange();
        }
    }

    private void receiveAgentCommand(GameControllerSocket socket, AgentCommand command) {
        Agent agent = socketMapping.get(socket);
        if (agent == null) {
            return;
        } else if (agent == servedGamed.getCurrentAgent()) {
            servedGamed.getCurrentAgent().accept(command);
        } /* else if proszivo robot then... */
    }

    public GameControllerSocket createSocketForAgent(Agent agent) {
        synchronized (mappingOperationLock) {
            if (!agentMapping.containsKey(agent)) {
                ControlSocket newSocket = new ControlSocket(this);
                agentMapping.put(agent, newSocket);
                socketMapping.put(newSocket, agent);
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
