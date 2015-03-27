package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import agents.Agent;
import field.Field;

public class Game implements ControllerListener, HeartbeatListener {
    private final int roundTime;

    private final ArrayList<Player> players;
    private final ArrayList<Player> disqualified;
    private final Map map;
    private final AgentController controller;
    private Player currentPlayer;

    public Game(ArrayList<Player> players, Map map, int roundTime) {
        this.roundTime = roundTime;

        this.players = players;
        disqualified = new ArrayList<Player>();
        currentPlayer = this.players.get(0);

        this.map = map;
        controller = new HumanController(this);

        placeAgents();
        Heartbeat.subscribe(this);
    }

    public void start() {
        Heartbeat.resume();
    }

    public void pause() {
        Heartbeat.pause();
    }

    public void reset() {
        players.addAll(disqualified);
        disqualified.clear();
        for (Player player : players) {
            player.setTimeRemaining(roundTime);
        }
        placeAgents();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getDisqualified() {
        return disqualified;
    }

    public void registerController(Component component) {
        component.addKeyListener(controller);
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
        Player player = getCurrentPlayer();

        if (player == null) {
            return;
        }

        int currentIndex = players.indexOf(player);

        if (player.isOutOfTime() || player.getAgent().isDead()) {
            players.remove(player);
            disqualified.add(player);
            currentIndex -= 1;
        }

        if (players.isEmpty()) {
            pause();
            setCurrentPlayer(null);
            System.out.println("Game finished");
            return;
        }

        setCurrentPlayer(players.get((currentIndex + 1) % players.size()));
    }

    @Override
    public void onTick(long deltaTime) {
        if (players.isEmpty()) {
            Heartbeat.unsubscribe(this);
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
}
