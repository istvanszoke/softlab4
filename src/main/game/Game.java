package game;

import agents.Agent;
import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game implements ControllerListener {
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> disqualified = new ArrayList<Player>();

    private Player currentPlayer = null;
    private boolean isPaused = true;
    private int roundTime;

    private AgentController controller = new HumanController(this);

    ArrayList<Field> fields = new ArrayList<Field>();

    Timer timer = new Timer();

    public Game(int roundTime) {
        this.roundTime = roundTime;
        players.add(Player.createRobot(roundTime));
        players.add(Player.createRobot(roundTime));
        currentPlayer = players.get(0);

        fields.add(new FieldCell(-10));
        fields.add(new FieldCell(0));
        fields.add(new FieldCell(1));
        fields.add(new EmptyFieldCell(-10));

        fields.get(1).addNeighbour(Direction.UP, fields.get(2));
        fields.get(1).addNeighbour(Direction.DOWN, fields.get(3));

        fields.get(2).addNeighbour(Direction.DOWN, fields.get(1));
        fields.get(2).addNeighbour(Direction.UP, fields.get(3));

        fields.get(0).onEnter(players.get(0).getAgent());
        fields.get(1).onEnter(players.get(1).getAgent());

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
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void registerController(Component component) {
        component.addKeyListener(controller);
    }

    public Agent getCurrentAgent() {
        return getCurrentPlayer().getAgent();
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
}
