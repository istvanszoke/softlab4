package game;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import agents.Agent;
import buff.Buff;
import buff.Oil;
import field.Field;
import game.control.GameControllerServer;
import game.control.GameControllerServerListener;
import game.control.GameControllerSocket;
import game.control.HumanController;
import game.handle.AgentHandle;
import game.handle.HandleListener;


public class Game implements GameControllerServerListener, HeartbeatListener, HandleListener {
    private final List<GameListener> listeners;
    private final GameStorage gameStorage;
    private final Map map;

    private final GameControllerServer controllerServer;
    private final HumanController humanController;

    public Game(List<AgentHandle> agents, Map map) {
        this(new GameStorage(agents), map);
    }

    public Game(GameStorage inGameStorage, Map inMap) {
        listeners = new ArrayList<GameListener>();
        controllerServer = new GameControllerServer(this);
        humanController = new HumanController();

        gameStorage = inGameStorage;
        this.map = inMap;

        setAgentControllers();
        Heartbeat.subscribe(gameStorage);
        Heartbeat.subscribe(this);
    }

    public Game(java.util.Map<AgentHandle, Integer> agents, Map map,
                java.util.Map<Buff, Integer> buffs) {
        listeners = null;
        controllerServer = null;
        humanController = null;

        gameStorage = new GameStorage(agents.keySet());
        this.map = map;

        for (java.util.Map.Entry<AgentHandle, Integer> a : agents.entrySet()) {
            map.get(a.getValue()).onEnter(a.getKey().getAgent());
        }

        for (java.util.Map.Entry<Buff, Integer> b : buffs.entrySet()) {
            map.get(b.getValue()).placeBuff(b.getKey());
        }

        printOutMap(10, 3);
    }

    public void start() {
        register(gameStorage.getCurrent());
        Heartbeat.resume();
    }

    public void pause() {
        Heartbeat.pause();
        deregister(gameStorage.getCurrent());
    }

    public void registerController(Component component) {
        component.addKeyListener(humanController);
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public Map getMap() {
        return map;
    }

    public GameStorage getGameStorage() {return gameStorage; }

    @Override
    public void onAgentChange(Agent agent) {
        Heartbeat.pause();
        AgentHandle handle = gameStorage.get(agent);

        if (handle == null) {
            return;
        }

        handle.onTurnEnd();
    }

    @Override
    public void onRegularTurn(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();
        register(gameStorage.getCurrent());

        Heartbeat.resume();
    }

    @Override
    public void onOutOfTime(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();

        System.out.println("Timed out " + handle);

        if (isGameOver()) {
            endGame();
            return;
        }

        register(gameStorage.getCurrent());

        Heartbeat.resume();
    }


    @Override
    public void onAgentDeath(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();

        if (isGameOver()) {
            endGame();
            return;
        }

        register(gameStorage.getCurrent());
        Heartbeat.resume();
    }


    @Override
    public void onTick(long deltaTime) {
        //TODO:Spawn vacuum robots or do anything time related
    }

    private void placeAgents() {
        List<AgentHandle> inPlay = gameStorage.getInPlay();
        List<Field> startingFields = map.findStartingPositions(inPlay.size());

        for (int i = 0; i < inPlay.size(); ++i) {
            Agent agent = inPlay.get(i).getAgent();
            Field startingField = agent.getField();

            if (startingField != null) {
                startingField.onExit();
            }

            startingFields.get(i).onEnter(agent);
        }
    }

    private void setAgentControllers() {
        for (AgentHandle player : gameStorage) {
            Agent agent = player.getAgent();
            GameControllerSocket socket = controllerServer.createSocketForAgent(agent);
            humanController.addControllerSocket(socket);
        }
    }

    //TODO: Real game finishing logic
    private void endGame() {
        pause();
        Heartbeat.unsubscribe(gameStorage);
        Heartbeat.unsubscribe(this);

        for (GameListener listener: listeners) {
            listener.onGameFinished(gameStorage.getPlayers());
        }
    }

    private void register(AgentHandle handle) {
        controllerServer.notifyControllerSocketOpened(handle.getAgent());
        handle.register(this);
    }

    private void deregister(AgentHandle handle) {
        controllerServer.notifyControllerSocketClosed(handle.getAgent());
        handle.deregister(this);
    }

    private boolean isGameOver() {
        if (gameStorage.getPlayers().isEmpty()) {
            return true;
        }

        for (AgentHandle handle : gameStorage.getPlayers()) {
            if (!handle.isDisqualified()) {
                return false;
            }
        }

        return true;
    }

    private void printOutMap(int width, int cellWidth) {
        Iterator<Field> fieldIt = map.iterator();

        int totalWidth = (cellWidth+2)*width;
        while(fieldIt.hasNext()) {
            System.out.println(rowSeparator(totalWidth));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < width; ++i) {
                Field current = fieldIt.next();
                StringBuilder sbi = new StringBuilder();
                if (current != null) {
                    if (current.getFirstCleanableBuff() != null) {
                        sbi.append(current.getFirstCleanableBuff().toString());
                    }
                    if (current.getAgent() != null) {
                        sbi.append(current.getAgent().toString());
                    }
                }
                if (sbi.length() < cellWidth) {
                    int more = cellWidth-sbi.length();
                    for (int j = 0; j < more; ++j) {
                        sbi.append(' ');
                    }
                }
                sb.append('|');
                sb.append(sbi.toString());
                sb.append('|');
            }
            System.out.println(sb.toString());
        }
        System.out.println(rowSeparator(totalWidth) + "\n\n");

    }

    private String rowSeparator(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append('-');
        }
        return sb.toString();
    }
}
