package field;

import java.io.Serializable;

public class Displacement implements Serializable {
    private final Field start;
    private final Field goal;
    private boolean passedFinishLine;

    public Displacement(Field start, Field goal) {
        this.start = start;
        this.goal = goal;
        passedFinishLine = false;
    }

    public Displacement(Field start, Field goal, boolean passedFinishLine) {
        this.start = start;
        this.goal = goal;
        this.passedFinishLine = passedFinishLine;
    }

    public Field getStart() {
        return start;
    }

    public Field getGoal() {
        return goal;
    }

    public boolean passedFinishLine() {
        return passedFinishLine;
    }
}
