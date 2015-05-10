package game;

import java.util.ArrayList;
import java.util.List;

import field.*;
import game.handle.AgentHandle;

public final class GameCreator {
    private Map map;
    private List<AgentHandle> agents;

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

        Game game;
        try {
            game = new Game(agents, map);
        } catch (BadMapSizeException e) {
            return null;
        }

        return game;
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
                    Field f = new FinishLineFieldCell(Direction.UP);
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
