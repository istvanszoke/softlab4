package commands.executes;

import agents.Robot;
import agents.Speed;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeSpeedTransmit;

public class ChangeSpeedExecute extends AgentCommand {
    private int magnitudeDelta;

    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent);
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
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
            result.pushDebug("Changed speed for " + element + ", new speed is: " + newSpeed.getMagnitude());
        } else {
            result.pushDebug("Failed to change speed for " + element);
        }
    }
}
