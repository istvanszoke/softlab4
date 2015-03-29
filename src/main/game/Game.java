package game;

import agents.Agent;
import field.Field;
import game.control.GameControllerServer;
import game.control.GameControllerServerListener;
import game.control.GameControllerSocket;
import game.control.HumanController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class Game implements GameControllerServerListener, HeartbeatListener {
    private final int roundTime;

    private final ArrayList<Player> players;
    private final ArrayList<Player> disqualified;
    private final Map map;

    private final GameControllerServer controllerServer;
    private final HumanController humanController;
    private Player currentPlayer;

    public Game(ArrayList<Player> players, Map map, int roundTime) {
        controllerServer = new GameControllerServer(this);
        humanController = new HumanController();
        this.roundTime = roundTime;

        this.players = players;
        disqualified = new ArrayList<Player>();
        currentPlayer = players.get(0);

        this.map = map;

        placeAgents();
        setAgentControllers();
        Heartbeat.subscribe(this);
    }

    public void start() {
        controllerServer.notifyControllerSocketOpened(getCurrentAgent());
        Heartbeat.resume();
    }

    public void pause() {
        Heartbeat.pause();
        controllerServer.notifyControllerSocketClosed(getCurrentAgent());
    }

    public void reset() {
        pause();

        players.addAll(disqualified);
        disqualified.clear();
        for (Player player : players) {
            player.setTimeRemaining(roundTime * 1000);
        }

        for (Field f : map) {
            f.onExit();
        }
        placeAgents();

        setCurrentPlayer(players.get(0));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getDisqualified() {
        return disqualified;
    }

    public void registerController(Component component) {
        component.addKeyListener(humanController);
    }

    public Agent getCurrentAgent() {
        Player player = getCurrentPlayer();

        if (player == null) {
            return null;
        }

        return player.getAgent();
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void onAgentChange() {
        Heartbeat.pause();
        Player player = getCurrentPlayer();

        if (player == null) {
            return;
        }
	
        controllerServer.notifyControllerSocketClosed(player.getAgent());
        int currentIndex = players.indexOf(player);

        if (player.isOutOfTime() || player.getAgent().isDead()) {
            players.remove(player);
            disqualified.add(player);
            currentIndex -= 1;
        }

        if (players.isEmpty()) {
            endGame();
            return;
        }

        setCurrentPlayer(players.get((currentIndex + 1) % players.size()));
        controllerServer.notifyControllerSocketOpened(getCurrentAgent());
        Heartbeat.resume();
    }

    @Override
    public void onTick(long deltaTime) {
        if (players.isEmpty()) {
            endGame();
            return;
        }

        Player player = getCurrentPlayer();

        player.setTimeRemaining(player.getTimeRemaining() - (int) deltaTime);

        if (player.isOutOfTime()) {
            System.out.println("Time out: " + player);
            onAgentChange();
        }
    }

    private synchronized Player getCurrentPlayer() {
        return currentPlayer;
    }

    private synchronized void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    private void placeAgents() {
        Collection<Field> startingFields = map.findStartingPositions(players.size());
        Field[] fields = startingFields.toArray(new Field[startingFields.size()]);

        for (int i = 0; i < players.size(); ++i) {
            Agent agent = players.get(i).getAgent();
            Field startingField = agent.getField();

            if (startingField != null) {
                startingField.onExit();
            }

            fields[i].onEnter(agent);
        }
    }

    private void setAgentControllers() {
        for (Player player : players) {
            Agent agent = player.getAgent();
            GameControllerSocket socket = controllerServer.createSocketForAgent(agent);
            humanController.addControllerSocket(socket);
        }
    }

    private void endGame() {
        pause();
        System.out.println("Game finished");
    }
}
