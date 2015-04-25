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
        isCleanable = true;
    }

    @Override
    public void visit(Robot element) {
        usesRemaining -= 1;
        if (usesRemaining == 0) {
            System.out.println("Ragacs elkopott.");
            remove();
            return;
        }

        Speed oldSpeed = element.getSpeed();
        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        System.out.println("ragacsfelez 0 " + oldSpeed.getMagnitude() + " " + newSpeed.getMagnitude());
    }

    @Override
    public void visit(Vacuum element) {

    }

    @Override
    public String toString() {
        return "S";
    }

    @Override
    public void accept(BuffVisitor visitor) {
        visitor.visit(this);
    }
}
