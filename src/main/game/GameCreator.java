package game;

import field.*;

import java.util.ArrayList;

public final class GameCreator {
    private Map map = null;
    private ArrayList<Field> fields = new ArrayList<Field>();
    private ArrayList<Field> finishLineCells = new ArrayList<Field>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int roundTime = -1;

    public GameCreator setRoundTime(int roundTime) {
        this.roundTime = roundTime;
        return this;
    }

    public GameCreator addPlayer(Player player) {
        players.add(player);
        placeAgents();
        return this;
    }

    public GameCreator setMap(Map map) {
        this.map = map;
        return this;
    }

    public GameCreator generateTestMap(int width, int height) {
        generateGrid(width, height);
        setNeighbours(width, height);
        placeAgents();
        return this;
    }

    public Game create() {
        if ((fields.isEmpty() && map == null) || finishLineCells.isEmpty() ||
                players.size() < 2 || players.size() > 4 || roundTime == -1) {
            return null;
        }

        if (map == null) {
            map = new Map(fields);
        }

        return new Game(players, map, roundTime);
    }

    private void generateGrid(int width, int height) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int distanceFromGoal = i < height / 2 ? height / 2 - i : i - height / 2;
                Field field;

                if (i == height / 2) {
                    field = new FinishLineFieldCell();
                    finishLineCells.add(field);
                } else {
                    field = new FieldCell(distanceFromGoal);
                }

                fields.add(field);
            }
        }
    }

    private void setEdges(Field field, Direction direction) {
        field.addNeighbour(direction, new EmptyFieldCell(-1));
    }

    private void setNeighbours(int width, int height) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Field field = fields.get(i * width + j);

                if (i > 0 && j > 0 && i < height - 1 && j < width - 1) {
                    field.addNeighbour(Direction.UP, fields.get((i - 1) * width + j));
                    field.addNeighbour(Direction.DOWN, fields.get((i + 1) * width + j));
                    field.addNeighbour(Direction.LEFT, fields.get(i * width + j - 1));
                    field.addNeighbour(Direction.RIGHT, fields.get(i * width + j + 1));
                }

                if (i == 0) {
                    setEdges(field, Direction.UP);
                } else if (i == height - 1) {
                    setEdges(field, Direction.DOWN);
                }

                if (j == 0) {
                    setEdges(field, Direction.LEFT);
                } else if (j == width - 1) {
                    setEdges(field, Direction.RIGHT);
                }
            }
        }
    }

    private void placeAgents() {
        if (finishLineCells.size() < players.size()) {
            System.out.println("Not enough finish line cells.");
            return;
        }

        for (Field field : finishLineCells) {
            field.onExit();
        }

        int cell = 0;
        for (Player player : players) {
            finishLineCells.get(cell++).onEnter(player.getAgent());
        }
    }
}
