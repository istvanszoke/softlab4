package game;

import java.util.ArrayList;
import java.util.List;

import field.*;
import game.handle.AgentHandle;

/**
 * Játékot generáló osztály
 * Feladata, hogy beállíthatjuk rajta a játéknak a paramétereit ezzel a játékosok számát
 * , hogy egy játékos mennyi időt kaphat stb. És ezekből elkészíti a megfelelő játéklogikát
 */
public final class GameCreator {
    /**
     * A létrehozott Pályának a referenciája
     */
    private Map map;
    private List<AgentHandle> agents;

    /**
     * Játék generáló osztály konstruktora
     */
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

    /**
     * Új pálya generálása
     * A játékhoz lehet új pályát generlni ezzel a fügvénnyel a megadott méretben
     *
     * @param width  - A létrehozott pálya szélessége a pálya mezőinek számában
     * @param height - A létrehozott pálya hosszúsága a pálya mezőinek számában
     * @return
     */
    public GameCreator generateTestMap(int width, int height) {
        generateGrid(width, height);
        setNeighbours(width, height);
        return this;
    }

    /**
     * Játéklogika generálása
     * Létrehozza a megfelelő játéklogikát, hogy ha nem adtunk meg érvénytelen paramétereket
     *
     * @return - A kigenerált játék referenciája
     */
    public Game create() {
        if (map.isEmpty() || agents.size() < 2 || agents.size() > 4) {
            return null;
        }

        return new Game(agents, map);
    }

    /**
     * Pálya rács létrehozása
     * Létrehoz egy megfelelő méretű pályarácsot
     *
     * @param width  - A létrehozott rács szélessége a pálya mezőinek számában
     * @param height - A létrehozott rács hosszúsága a pálya mezőinek számában
     */
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

    /**
     * Mezők szomszédjainak megadása
     * Beállítja az összes pályán lévő mezőnek, hogy melyek az ő közvetlen szomszédai
     *
     * @param width  - A pálya szélessége a mezők számában
     * @param height - A pálya hosszúsága a mezők számában
     */
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
