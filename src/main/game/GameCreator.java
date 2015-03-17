package game;

import java.util.ArrayList;

import field.*;

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
    /**
     * A Játékosoknak a tömbje
     */
    private ArrayList<Player> players;
    /**
     * Körideje egy játékosnak
     */
    private int roundTime;

    /**
     * Játék generáló osztály konstruktora
     */
    public GameCreator() {
        map = new Map();
        players = new ArrayList<Player>();
        roundTime = -1;
    }

    /**
     * Körídő beállítása
     * Játékosok köridejét lehet vele meghatározni másodpercben
     * @param roundTime - A Játékosoknak szánt köridő másodpercben
     * @return - Az játék generáló osztály saját referenciája a módosítás után
     */
    public GameCreator setRoundTime(int roundTime) {
        this.roundTime = roundTime;
        return this;
    }

    /**
     * Játékos hozzáadása
     * További játékosokat tudunk hozzáadni a játékhoz
     * @param player - Az őjonnan hozzáadott játékos referenciája
     * @return - Az játék generáló osztály saját referenciája a módosítás után
     */
    public GameCreator addPlayer(Player player) {
        players.add(player);
        return this;
    }

    /**
     * Meglévő pálya beállítása
     * Egy már létező pályát beállíthatunk hogy a generált játéklogika majd ezen működjön
     * @param map - A már létező pálya referenciája
     * @return - Az játék generáló osztály saját referenciája a módosítás után
     */
    public GameCreator setMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * Új pálya generálása
     * A játékhoz lehet új pályát generlni ezzel a fügvénnyel a megadott méretben
     * @param width - A létrehozott pálya szélessége a pálya mezőinek számában
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
     * @return - A kigenerált játék referenciája
     */
    public Game create() {
        if (map.isEmpty() || players.size() < 2 || players.size() > 4 || roundTime == -1) {
            return null;
        }

        return new Game(players, map, roundTime);
    }

    /**
     * Pálya rács létrehozása
     * Létrehoz egy megfelelő méretű pályarácsot
     * @param width - A létrehozott rács szélessége a pálya mezőinek számában
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
     * @param width - A pálya szélessége a mezők számában
     * @param height - A pálya hosszúsága a mezők számában
     */
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
