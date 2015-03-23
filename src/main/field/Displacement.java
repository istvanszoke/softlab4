package field;

import inspector.Inspector;

public class Displacement {
    private final Field start;
    private final Field goal;

    public Displacement(Field start, Field goal) {
        Inspector.call("Displacement.Displacement(Field, Field)");
        this.start = start;
        this.goal = goal;
        Inspector.ret("Displacement.Displacement");
    }

    public Field getStart() {
        Inspector.call("Displacement.getStart():Field");
        Inspector.ret("Displacement.getStart");
        return start;
    }

    public Field getGoal()
    {
        Inspector.call("Displacement.getGoal():Field");
        Inspector.ret("Displacement.getGoal");
        return goal;
    }
}
