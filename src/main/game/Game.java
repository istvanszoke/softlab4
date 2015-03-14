package game;

import agents.Robot;
import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private int currentPlayerIndex = -1;

    ArrayList<Field> fields = new ArrayList<Field>();

    public Game() {
        players.add(Player.createHuman(new Robot(), 5));
        players.add(Player.createHuman(new Robot(), 1000));
        currentPlayerIndex = 0;

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
        System.out.println("Processing robot with index: " + currentPlayerIndex);

        final Player currentPlayer = players.get(currentPlayerIndex);
        final ExecutorService controlExecutor = Executors.newSingleThreadExecutor();

        try {
            controlExecutor.submit(getAgentJob()).get(currentPlayer.getTimeRemaining(),
                                                      TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            currentPlayer.getAgent().timeOut();
            currentPlayer.setTimeRemaining(0);
        } catch (Exception ignored) {
            // We don't care about other exceptions
        } finally {
            currentPlayer.deactivate();
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            controlExecutor.shutdown();
        }
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        currentPlayerIndex = 0;
    }

    public void registerControllers(Component component) {
        for (Player player : players) {
            component.addKeyListener(player.getController());
        }
    }


    private Callable<Void> getAgentJob() {
        final Player currentPlayer = players.get(currentPlayerIndex);

        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long startTime = System.currentTimeMillis();
                currentPlayer.activate();
                long elapsed = System.currentTimeMillis() - startTime;
                currentPlayer.setTimeRemaining(currentPlayer.getTimeRemaining() - (int) elapsed);
                return null;
            }
        };
    }
}
