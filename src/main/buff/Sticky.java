package buff;

import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import feedback.NoFeedbackException;
import feedback.Result;

public class Sticky extends Buff {
    int usesRemaining;

    public Sticky() {
        usesRemaining = 4;
    }

    @Override
    public void visit(Robot element) {
        if (usesRemaining == 0) {
            System.out.println("Sticky has worn off.");
            remove();
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        usesRemaining -= 1;
        isCleanable = true;
    }

    @Override
    public void visit(Vacuum element) {

    }

    @Override
    public String toString() {
        return "S";
    }
}
