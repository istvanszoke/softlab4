package field;

import inspector.Inspector;

/**
 * Elmozdulást kezelő osztály.
 * Kezdeti és végpont mezőket tárol.
 */
public class Displacement {
    /**
     * Kezdet
     */
    private final Field start;
    
    /**
     * Cél
     */
    private final Field goal;

    /**
     * Konstruktor
     * @param start - KezdőField
     * @param goal - CélField
     */
    public Displacement(Field start, Field goal) {
        Inspector.call("Displacement.Displacement(Field, Field)");
        this.start = start;
        this.goal = goal;
        Inspector.ret("Displacement.Displacement");
    }

    /**
     * Getter a startra.
     * @return start - start
     */
    public Field getStart() {
        Inspector.call("Displacement.getStart():Field");
        Inspector.ret("Displacement.getStart");
        return start;
    }
    
    /**
     * Getter a goalra.
     * @return goal - goal
     */
    public Field getGoal()
    {
        Inspector.call("Displacement.getGoal():Field");
        Inspector.ret("Displacement.getGoal");
        return goal;
    }
}
