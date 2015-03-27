package field;

public class Displacement {
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
