package game;

import field.*;

import java.util.ArrayList;

public final class GameCreator {
    private Map map = new Map();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int roundTime = -1;

    public GameCreator setRoundTime(int roundTime) {
        this.roundTime = roundTime;
        return this;
    }

    public GameCreator addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public GameCreator setMap(Map map) {
        this.map = map;
        return this;
    }

    public GameCreator generateTestMap(int width, int height) {
        generateGrid(width, height);
        setNeighbours(width, height);
        return this;
    }

    public Game create() {
        if (map.isEmpty() || players.size() < 2 || players.size() > 4 || roundTime == -1) {
            return null;
        }

        return new Game(players, map, roundTime);
    }

    private void generateGrid(int width, int height) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int distanceFromGoal = i < height / 2 ? height / 2 - i : i - height / 2;

                if (i == height / 2) {
                    map.add(new FinishLineFieldCell());
                } else {
                    map.add(new FieldCell(distanceFromGoal));
                }
            }
        }
    }

    private void setNeighbours(int width, int height) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Field field = map.get(i * width + j);

                if (i > 0) {
                    field.addNeighbour(Direction.UP, map.get((i - 1) * width + j));
                } else if (i < height - 1) {
                    field.addNeighbour(Direction.DOWN, map.get((i + 1) * width + j));
                }

                if (i == 0) {
                    field.addNeighbour(Direction.UP, new EmptyFieldCell(-1));
                } else if (i == height - 1) {
                    field.addNeighbour(Direction.DOWN, new EmptyFieldCell(-1));
                }

                if (j > 0) {
                    field.addNeighbour(Direction.LEFT, map.get(i * width + j - 1));
                } else if (j < width - 1) {
                    field.addNeighbour(Direction.RIGHT, map.get(i * width + j + 1));
                }

                if (j == 0) {
                    field.addNeighbour(Direction.LEFT, new EmptyFieldCell(-1));
                } else if (j == width - 1) {
                    field.addNeighbour(Direction.RIGHT, new EmptyFieldCell(-1));
                }
            }
        }
    }
}
