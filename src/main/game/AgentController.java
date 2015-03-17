package game;

import java.awt.event.KeyAdapter;

/**
 * Absztrakt ágens kontroller
 * Egy egységes ősolysztály aminden leendő Ágens vezérlőnek
 */
public abstract class AgentController extends KeyAdapter {
    protected final Game game;

    /**
     * Ágens kontroller konstruktor
     * Beállítja a Játékra való referencát mellyel a gyermek osztályok majd el tudják érni
     * a játéklogiát és használni tudják a funkcióit, irányítani tudják ágenseiket
     * @param game
     */
    public AgentController(Game game) {
        this.game = game;
    }
}