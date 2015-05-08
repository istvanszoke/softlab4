package field;

import java.io.Serializable;

public class Displacement implements Serializable {
    private final Field start;
    private final Field goal;

    public Displacement(Field start, Field goal) {
        this.start = start;
        this.goal = goal;
    }

    public Field getStart() {
        return start;
    }

    public Field getGoal() {
        return goal;
    }
}
