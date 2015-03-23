package buff;

import agents.Robot;
import agents.Speed;
import inspector.Inspector;

public class Sticky extends Buff {
    @Override
    public void visit(Robot element) {
        Inspector.call("Sticky.visit(Robot)");
        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        System.out.println("Sticky changed speed, new speed is: " + newSpeed);
        Inspector.ret("Sticky.visit");
    }
}
