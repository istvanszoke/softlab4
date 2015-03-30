package game;

import java.util.ArrayList;

import field.*;
import game.handle.AgentHandle;

public final class GameCreator {
    private Map map;
    private ArrayList<AgentHandle> agents;

    public GameCreator() {
        map = new Map();
        agents = new ArrayList<AgentHandle>();
    }

    public GameCreator addAgent(AgentHandle player) {
        agents.add(player);
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
        if (map.isEmpty() || agents.size() < 2 || agents.size() > 4) {
            return null;
        }

        return new Game(agents, map);
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
                }

                if (i < height - 1) {
                    field.addNeighbour(Direction.DOWN, map.get((i + 1) * width + j));
                }

                if (i == 0) {
                    field.addNeighbour(Direction.UP, new EmptyFieldCell(-1));
                }
                if (i == height - 1) {
                    field.addNeighbour(Direction.DOWN, new EmptyFieldCell(-1));
                }

                if (j > 0) {
                    field.addNeighbour(Direction.LEFT, map.get(i * width + j - 1));
                }
                if (j < width - 1) {
                    field.addNeighbour(Direction.RIGHT, map.get(i * width + j + 1));
                }

                if (j == 0) {
                    field.addNeighbour(Direction.LEFT, new EmptyFieldCell(-1));
                }
                if (j == width - 1) {
                    field.addNeighbour(Direction.RIGHT, new EmptyFieldCell(-1));
                }
            }
        }
    }
}
