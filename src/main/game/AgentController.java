package game;

import java.awt.event.KeyAdapter;

/**
 * Absztrakt ágens kontroller
 * Egy egységes ősolysztály aminden leendő Ágens vezérlőnek
 */
public abstract class AgentController extends KeyAdapter {
    /**
     * Eltárolt referencia a játéklogikára
     */
    protected final Game game;

    /**
     * Ágens kontroller konstruktor
     * Beállítja a Játékra való referencát mellyel a gyermek osztályok majd el tudják érni
     * a játéklogiát és használni tudják a funkcióit, irányítani tudják ágenseket
     * @param game - A referencia a felhasznált játéklogikára
     */
    public AgentController(Game game) {
        this.game = game;
    }
}