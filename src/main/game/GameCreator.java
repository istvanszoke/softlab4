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
        generateGrid(width, height);
        return this;
    }

    public Game create() {
        if (map.isEmpty() || agents.size() < 2 || agents.size() > 4) {
            return null;
        }

        return new Game(agents, map);
    }

    public Map getMap() {
        return map;
    }

    private void generateGrid(int width, int height) {
        List<Field> fields = new ArrayList<Field>();
        List<Field> finishLineFields = new ArrayList<Field>();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int distanceFromGoal = i < height / 2 ? height / 2 - i : i - height / 2;

                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    fields.add(new EmptyFieldCell(-1));
                } else if (i == height / 2 && j != width - 2) { // Workaround for non-circular map
                    Field f = new FinishLineFieldCell();
                    fields.add(f);
                    finishLineFields.add(f);
                } else {
                    fields.add(new FieldCell(distanceFromGoal));
                }
            }
        }

        map = new Map(width, height, fields, finishLineFields);
    }
}
