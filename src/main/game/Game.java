package game;

import java.util.*;

import agents.Agent;
import agents.AgentVisitor;
import agents.Robot;
import agents.Vacuum;
import buff.Buff;
import feedback.NoFeedbackException;
import feedback.Result;
import field.Field;
import game.control.*;
import game.handle.AgentHandle;
import game.handle.HandleListener;
import game.handle.VacuumHandle;


public class Game implements GameControllerServerListener, HeartbeatListener, HandleListener {
    private static final int MAX_VACUUMS = 2;
    private static final int VACUUM_SPAWN_INTERVAL = 30000; // 30 sec

    private final List<GameListener> listeners;
    private final GameStorage gameStorage;
    private final Map map;

    private final GameControllerServer controllerServer;
    private HumanController humanController;

    private final List<VacuumController> vacuumControllers;

    private int vacuumSpawnElapsed = 0;

    public Game(List<AgentHandle> agents, Map map) {
        this(new GameStorage(agents), map);
        placeAgents();
    }

    public Game(GameStorage inGameStorage, Map inMap) {
        listeners = new ArrayList<GameListener>();
        controllerServer = new GameControllerServer(this);
        humanController = null;
        vacuumControllers = new ArrayList<VacuumController>();

        gameStorage = inGameStorage;
        this.map = inMap;
    }

    public Game(java.util.Map<AgentHandle, Integer> agents, Map map,
                java.util.Map<Buff, Integer> buffs) {
        listeners = null;
        controllerServer = null;
        humanController = null;
        vacuumControllers = null;

        gameStorage = new GameStorage(agents.keySet());
        this.map = map;

        for (java.util.Map.Entry<AgentHandle, Integer> a : agents.entrySet()) {
            map.get(a.getValue()).onEnter(a.getKey().getAgent());
        }

        for (java.util.Map.Entry<Buff, Integer> b : buffs.entrySet()) {
            map.get(b.getValue()).placeBuff(b.getKey());
        }
    }

    public void initialize(HumanController controller) {
        humanController = controller;
        setAgentControllers();
        Heartbeat.subscribe(gameStorage);
        Heartbeat.subscribe(this);
    }

    public void start() {
        register(gameStorage.getCurrent());
        Heartbeat.resume();
    }

    public void pause() {
        Heartbeat.pause();
        deregister(gameStorage.getCurrent());
    }

    public AgentHandle getAgentHandle(Agent agent) {
        return gameStorage.get(agent);
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
        for (GameListener listener : listeners) {
            listener.onAgentChange();
        }
    }

    @Override
    public void onRegularTurn(AgentHandle handle) {
        Heartbeat.pause();

        deregister(handle);
        gameStorage.update();
        MapPrinter.print(map, 3);
        register(gameStorage.getCurrent());

        Heartbeat.resume();
    }

    @Override
    public void onOutOfTime(AgentHandle handle) {
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
        pause();

        vacuumSpawnElapsed += deltaTime;
        if (vacuumSpawnElapsed < VACUUM_SPAWN_INTERVAL) {
            start();
            return;
        }

        cleanupDeadVacuums();
        int vacuumsToSpawn = vacuumControllers.size() - MAX_VACUUMS;
        if (vacuumsToSpawn >= 0) {
            start();
            return;
        }

        List<Field> unoccupiedFields = map.findUnoccupied();
        Random random = new Random(System.currentTimeMillis());
        ControllerAssigner assigner = new ControllerAssigner(controllerServer);

        while (vacuumsToSpawn != 0) {
            Vacuum vacuum = new Vacuum();
            VacuumHandle handle = new VacuumHandle(vacuum);
            int fieldIndex = random.nextInt(unoccupiedFields.size());

            gameStorage.add(handle);
            unoccupiedFields.get(fieldIndex).onEnter(vacuum);
            assigner.visit(vacuum);
            unoccupiedFields.remove(fieldIndex);

            ++vacuumsToSpawn;
        }
        vacuumSpawnElapsed = 0;
        start();
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
        ControllerAssigner assigner = new ControllerAssigner(controllerServer);
        for (AgentHandle player : gameStorage) {
            Agent agent = player.getAgent();
            agent.accept(assigner);
        }
    }

    private void endGame() {
        pause();
        Heartbeat.unsubscribe(gameStorage);
        Heartbeat.unsubscribe(this);

        List<GameListener> listenersCopy = new ArrayList<GameListener>(listeners.size());
        for (GameListener listener : listeners) {
            listenersCopy.add(listener);
        }

        for (GameListener listener: listenersCopy) {
            listener.onGameFinished(gameStorage.getPlayers());
        }

        for (AgentHandle a : gameStorage.getAll()) {
            controllerServer.removeAgent(a.getAgent());
        }
        gameStorage.clear();
    }

    private void register(AgentHandle handle) {
        handle.register(this);
        controllerServer.notifyControllerSocketOpened(handle.getAgent());
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

    private void cleanupDeadVacuums() {
        Iterator<VacuumController> i = vacuumControllers.iterator();
        while (i.hasNext()) {
            VacuumController c = i.next();
            Vacuum v = c.getVacuum();
            if (v.isDead()) {
                controllerServer.removeAgent(v);
                i.remove();
            }
        }
    }


    private class ControllerAssigner implements AgentVisitor {
        GameControllerServer server;

        ControllerAssigner(GameControllerServer server) {
            this.server = server;
        }

        @Override
        public void visit(Robot element) {
            GameControllerSocket socket = controllerServer.createSocketForAgent(element);
            if (humanController == null) {
                throw new IllegalStateException();
            }
            humanController.addControllerSocket(socket);
        }

        @Override
        public void visit(Vacuum element) {
            GameControllerSocket socket = controllerServer.createSocketForAgent(element);
            if (socket == null) {
                return;
            }

            VacuumController controller = new VacuumController(element, map);
            vacuumControllers.add(controller);
            socket.enableStateNotification(controller);
        }

        @Override
        public Result getResult() throws NoFeedbackException {
            return null;
        }

    }
}
