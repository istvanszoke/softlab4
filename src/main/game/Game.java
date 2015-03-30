package game;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import agents.Agent;
import field.Field;
import game.control.GameControllerServer;
import game.control.GameControllerServerListener;
import game.control.GameControllerSocket;
import game.control.HumanController;
import game.handle.AgentHandle;
import game.handle.HandleListener;


public class Game implements GameControllerServerListener, HeartbeatListener, HandleListener {
    private final GameStorage gameStorage;
    private final Map map;

    private final GameControllerServer controllerServer;
    private final HumanController humanController;

    public Game(ArrayList<AgentHandle> agents, Map map) {
        controllerServer = new GameControllerServer(this);
        humanController = new HumanController();

        gameStorage = new GameStorage(agents);

        this.map = map;

        placeAgents();
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

    public void registerController(Component component) {
        component.addKeyListener(humanController);
    }

    public Map getMap() {
        return map;
    }

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

        System.out.println("Killed " + handle);

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
        System.out.println("Game finished");
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
}
