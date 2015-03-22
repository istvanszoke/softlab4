package field;

/*
 * Elmozdulást kezelõ osztály.
 * Kezdeti és végpont mezõket tárol.
 */
public class Displacement {
    /*
     * Kezdet
     */
    private final Field start;
    
    /*
     * Cél
     */
    private final Field goal;

    /*
     * Konstruktor
     * @param start - KezdõField
     * @param goal - CélField
     */
    public Displacement(Field start, Field goal) {
        this.start = start;
        this.goal = goal;
    }

    /*
     * Getter a startra.
     * @return start - start
     */
    public Field getStart() {
        return start;
    }
    
    /*
     * Getter a goalra.
     * @return goal - goal
     */
    public Field getGoal() {
        return goal;
    }
}
