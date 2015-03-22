package field;

/*
 * Elmozdul�st kezel� oszt�ly.
 * Kezdeti �s v�gpont mez�ket t�rol.
 */
public class Displacement {
    /*
     * Kezdet
     */
    private final Field start;
    
    /*
     * C�l
     */
    private final Field goal;

    /*
     * Konstruktor
     * @param start - Kezd�Field
     * @param goal - C�lField
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
