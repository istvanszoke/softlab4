package game.control;

import java.util.HashMap;
import java.util.Map;

import agents.Agent;
import commands.AgentCommand;

/**
 * Játékvezérlési kiszolgáló
 * Feladata, hogy az emberi bemenetet valamint a szükséges
 * mesterséges intellligenciákat illessze a játékhoz
 */
public class GameControllerServer {
    /**
     * Műveleti zár a hozzárendelési módosítások kezeléséhez
     */
    private final Object mappingOperationLock;
    /**
     * Kiszolgáló által generált eseményekre figyelő objektum referenciája
     */
    private GameControllerServerListener listener;
    /**
     * Csatlakozókat ágenesekhez rendelő adatszerkezet
     */
    private Map<ControlSocket, Agent> socketMapping;
    /**
     * Ágenseket csatlakozókhoz rendelő adatszerkezet
     */
    private Map<Agent, ControlSocket> agentMapping;
    /**
     * Általános csatlakozókat kiszolgáló csatlakozókhoz rendelő adatszerkezet
     */
    private Map<GameControllerSocket, ControlSocket> globalToLocalMapping;

    /**
     * A kiszolgálónak a konstruktora
     *
     * @param listener - Az objektum ami a kiszolgáló eseményeit figyelni fogja
     */
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

    /**
     * Kör vége jelzés átvétele kliensektől
     *
     * @param client - A küldő kliens referenciája
     * @return - Visszatérés a kliens igényének teljesüléséről
     */
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

    /**
     * Ágensnek küldött utasítás átvétele egy klienstől
     *
     * @param socket  - A csatlakozó amelyen keresztül az utasítás érkezett
     * @param command - Az ágesnsutasítás amit végre kell hajtani
     * @return - Visszajelzés a kérés célbajuttatásáról
     */
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

    /**
     * Létrehoz egy csatlakozót egy Ágensnek az irányításához
     *
     * @param agent - Az ágens amelyhez csatlakozót hoz létre
     * @return - A csatlakozó interfész referenciája
     */
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

    /**
     * Egy ágenshez tartozó csatlakozó eltávolítása a kiszolgálás elől
     *
     * @param agent - Az ágens amelyhez rendelt csatlakozót el akarjuk távolítani
     */
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

    /**
     * Ágenshez tartozó csatlakozó kinyitása és nyitás jelzése a tulaj felé
     *
     * @param agent - Ágens amelyhez tartozó csatlakozót nyitjuk
     */
    public void notifyControllerSocketOpened(Agent agent) {
        if (agentMapping.containsKey(agent)) {
            agentMapping.get(agent).notifySocketOpened();
        }
    }

    /**
     * Ágenshez tartozó  csatlakozó bezárása és zárás jelzése a tulaj felé
     *
     * @param agent - Ágens amelyhez tartozó csatlakozót zárjuk
     */
    public void notifyControllerSocketClosed(Agent agent) {
        if (agentMapping.containsKey(agent)) {
            agentMapping.get(agent).notifySocketClosed();
        }
    }

    /**
     * Általános csatlakozónak külvilágtól elzárt implementációja
     */
    private class ControlSocket implements GameControllerSocket {
        /**
         * Kliens amit a nyitás és zárásról értesítthetünk
         */
        GameControllerSocketListener clientToNofify = null;
        /**
         * A kiszolgáló aki a csatlakozót megynitotta
         */
        GameControllerServer server;
        /**
         * A csatlakozó jelenlegi nyitottsági állapota
         */
        boolean isOpened = false;

        /**
         * Csatlakozó konstruktora
         *
         * @param server - A kiszolgáló amely létrehozza
         */
        private ControlSocket(GameControllerServer server) {
            if (server != null) {
                this.server = server;
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Nyitottság lekérdezése a csatlakozótól
         *
         * @return - A nyitottsági állapot
         */
        @Override
        public boolean isOpen() { return isOpened; }

        /**
         * Jelezzük egy körnek a befejezését a kiszolgáló fele
         *
         * @return - Visszajelzés az jelzés sikerességéről
         */
        @Override
        public boolean sendEndTurn() {
            return isOpened && server.receiveEndTurn(this);
        }

        /**
         * Egy ágensnek szóló parancs elküldése a kliensnek
         *
         * @param command - Az utasítás amit elküldünk
         * @return - Visszajelzés a küldés sikeressségéről
         */
        @Override
        public boolean sendAgentCommand(AgentCommand command) {
            return isOpened && server.receiveAgentCommand(this, command);
        }

        /**
         * Nyitás és zárás eseményre való feliratkozás
         *
         * @param client - A kliens aki feliratkozik az eseményekre
         */
        @Override
        public void enableStateNotification(GameControllerSocketListener client) {
            clientToNofify = client;
        }

        /**
         * Leiratkozás a nyitás zárás eseményekről
         */
        @Override
        public void disableStateNotification() {
            clientToNofify = null;
        }

        /**
         * A kiszolgáló interfésze, hogy jelezze a csatlakozónak a nyitást
         */
        private void notifySocketOpened() {
            if (clientToNofify != null) {
                clientToNofify.socketOpened(this);
            }
            isOpened = true;
        }

        /**
         * A kiszolgláló interfésze, hogy jelezze a csatlakozónak a zárást
         */
        private void notifySocketClosed() {
            if (clientToNofify != null) {
                clientToNofify.socketClosed(this);
            }
            isOpened = false;
        }
    }
}
