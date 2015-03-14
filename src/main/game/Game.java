package game;

import agents.Agent;
import field.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class Game implements ControllerListener {
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> disqualified = new ArrayList<Player>();

    private Player currentPlayer;
    private boolean isPaused = true;
    private int roundTime;

    private Map map;
    private AgentController controller = new HumanController(this);

    Timer timer = new Timer();

    public Game(ArrayList<Player> players, Map map, int roundTime) {
        this.players = players;
        this.map = map;
        this.roundTime = roundTime;
        currentPlayer = this.players.get(0);
        placeAgents();

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

    private synchronized void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    private synchronized Player getCurrentPlayer() {
        return currentPlayer;
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
