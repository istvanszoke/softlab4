package game;

import java.awt.*;
import java.util.*;

import agents.Agent;
import field.Field;

public class Game implements ControllerListener {
    private final Timer timer;
    private boolean isPaused;
    private final int roundTime;

    private final ArrayList<Player> players;
    private final ArrayList<Player> disqualified;
    private Player currentPlayer;

    private final Map map;
    private final AgentController controller;

    public Game(ArrayList<Player> players, Map map, int roundTime) {
        timer = new Timer();
        isPaused = true;
        this.roundTime = roundTime;

        this.players = players;
        disqualified = new ArrayList<Player>();
        currentPlayer = this.players.get(0);

        this.map = map;
        controller = new HumanController(this);

        placeAgents();
        setupTimer();
    }

    public void start() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
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
        return getCurrentPlayer().getAgent();
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void onAgentChange() {
        int currentIndex = players.indexOf(currentPlayer);

        if (getCurrentPlayer().isOutOfTime()) {
            players.remove(getCurrentPlayer());
            disqualified.add(getCurrentPlayer());
            currentIndex -= 1;
        }

        if (players.isEmpty()) {
            pause();
            return;
        }

        setCurrentPlayer(players.get((currentIndex + 1) % players.size()));
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

    private void setupTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isPaused) {
                    return;
                }

                currentPlayer.setTimeRemaining(currentPlayer.getTimeRemaining() - 100);

                if (currentPlayer.isOutOfTime()) {
                    System.out.println("Time out: " + currentPlayer);
                    onAgentChange();
                }
            }
        }, 0, 100);
    }
}
