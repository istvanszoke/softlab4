package game;

import agents.Agent;
import agents.Robot;

/**
 * Egy játékost reprezentáló osztály
 */
public class Player {
    /**
     * A játékos saját Ágensére egy referencia
     */
    private Agent agent;
    /**
     * A játékos hátralévő ideje milliszekundumban
     */
    private int timeRemaining;

    /**
     * Új játékos létrehozása
     * Létre hoz egy új játékos mely egy Robotot fog irányítani
     * @param timeInSec - A játékos kezdőideje a másodpercben
     * @return - A létrehozott játékosnak a Referenciája
     */
    public static Player createRobot(int timeInSec) {
        Player instance = new Player();
        instance.agent = new Robot();
        instance.timeRemaining = timeInSec * 1000;
        return instance;
    }

    /**
     * Ágens referenciájának lekérése
     * Lekérdezhetjük, hogy melyik Ágens irányítását végzi az adott játékos
     * @return - Az irányított ágensnek a referenciája
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Hátralévő idő lekérdezése
     * Lekérdezhetjük a játékostól, hogy még mennyi idő áll a rendelkezésére
     * @return
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Hátralévő idő beállítása
     * Módosíthatjuk az Ágensen, hogy még mennyi ideje maradt a játékban
     * @param milliseconds - Az új ideje milliszekundumban
     */
    public void setTimeRemaining(int milliseconds) {
        timeRemaining = milliseconds;
    }

    /**
     * Lejárt idő lekérdezése
     * Lekérdezhetjük, hogy az adott játékosnak már elfogyott-e a rendelkezésre álló ideje
     * @return - Logikai válasz arra, hogy lejárt-e az ideje
     */
    public boolean isOutOfTime() {
        return timeRemaining <= 0;
    }
}
