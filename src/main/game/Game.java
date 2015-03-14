package game;

import agents.Agent;
import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;

import java.awt.*;
import java.util.ArrayList;

public class Game implements ControllerListener {
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> disqualified = new ArrayList<Player>();

    private Player currentPlayer = null;
    private boolean playerChanged = false;

    private AgentController controller = new HumanController(this);

    ArrayList<Field> fields = new ArrayList<Field>();

    public Game() {
        players.add(Player.createRobot(5));
        players.add(Player.createRobot(1000));
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
    }

    public void process() {
        long start = System.currentTimeMillis();
        long end = start + currentPlayer.getTimeRemaining();
        long current = start;
        long previous = start;

        while (current < end && !playerChanged) {
            long delta = current - previous;
            currentPlayer.setTimeRemaining(currentPlayer.getTimeRemaining() - (int)delta);

            previous = current;
            current = System.currentTimeMillis();
        }

        if (!playerChanged) {
            getCurrentAgent().timeOut();
        }

        onAgentChange();
        playerChanged = false;
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

        if (getCurrentAgent().isOutOfTime()) {
            players.remove(getCurrentPlayer());
            disqualified.add(getCurrentPlayer());
            currentIndex -= 1;
        }

        if (players.isEmpty()) {
            // Signal that the game is over
        }

        setCurrentPlayer(players.get((currentIndex + 1) % players.size()));

        playerChanged = true;
    }

    private synchronized void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    private synchronized Player getCurrentPlayer() {
        return currentPlayer;
    }
}
