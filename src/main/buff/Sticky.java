package buff;

import agents.Robot;
import agents.Speed;

public class Sticky extends Buff {
    @Override
    public void visit(Robot element) {
        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        System.out.println("Sticky changed speed, new speed is: " + newSpeed);
    }
}
