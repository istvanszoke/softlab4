package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;

public class ChangeSpeedExecute extends AgentCommand {
    private int magnitudeDelta;

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent.getResult(), parent.canExecute());
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        if (canExecute) {
            Speed newSpeed = element.getSpeed();
            newSpeed.setMagnitude(newSpeed.getMagnitude() + magnitudeDelta);
            element.setSpeed(newSpeed);
            result.pushMessage("Changed speed for " + element + ", new speed is: " + newSpeed.getMagnitude());
        } else {
            result.pushMessage("Failed to change speed for " + element);
        }
    }
}
