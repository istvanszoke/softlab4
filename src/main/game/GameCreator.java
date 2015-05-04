package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import agents.Robot;
import agents.Vacuum;
import field.*;
import game.handle.AgentHandle;

public final class GameCreator {
    private Map map;
    private List<AgentHandle> agents;

    public static boolean serializeGame(Game gameToSerialize, OutputStream output) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(output);

            oos.writeObject(gameToSerialize.getGameStorage());
            oos.writeObject(gameToSerialize.getMap());
            Robot.writeStaticParams(oos);
            Vacuum.writeStaticParams(oos);
            Field.writeStaticParams(oos);
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    public static Game deserializeGame(InputStream input) {
        try {
            ObjectInputStream ois = new ObjectInputStream(input);
            GameStorage restoredGameStorage = (GameStorage)ois.readObject();
            game.Map restoredMap = (game.Map)ois.readObject();
            Robot.readStaticParams(ois);
            Vacuum.readStaticParams(ois);
            Field.readStaticParams(ois);
            return new Game(restoredGameStorage, restoredMap);
        } catch (IOException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public GameCreator() {
        map = null;
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

    public GameCreator generateMap(int width, int height) {
        map = new Map(width, height);
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

    // Mapgen
    public Map getMap() {
        return map;
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
