package game;

import agents.Agent;
import agents.Robot;
import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player currentPlayer = null;

    ArrayList<Field> fields = new ArrayList<Field>();

    public Game() {
        players.add(Player.createHuman(this, 5));
        players.add(Player.createHuman(this, 1000));
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
        System.out.println("Processing player: " + currentPlayer);

        long start = System.currentTimeMillis();
        long end = start + currentPlayer.getTimeRemaining();

        while (System.currentTimeMillis() < end) {
            // ????
        }

        int currentIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentIndex + 1) % players.size());
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void registerControllers(Component component) {
        for (Player player : players) {
            component.addKeyListener(player.getController());
        }
    }

    public Agent getCurrentAgent() {
        return currentPlayer.getAgent();
    }
}
