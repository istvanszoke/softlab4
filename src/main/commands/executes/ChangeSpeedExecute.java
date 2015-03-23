package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

public class ChangeSpeedExecute extends AgentCommand {
    private int magnitudeDelta;

    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent);
        Inspector.call("ChangeSpeedExecute.ChangeSpeedExecute(ChangeSpeedTransmit)");
        this.magnitudeDelta = parent.getMagnitudeDelta();
        Inspector.ret("ChangeSpeedExecute.ChangeSpeedExecute");
    }

    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedExecute.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedExecute.getMagnitudeDelta");
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedExecute.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedExecute.setMagnitudeDelta");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeSpeedExecute.getFieldCommand()");
        Inspector.ret("ChangeSpeedExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeSpeedExecute.accept(AgentCommandVisitor");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedExecute.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeSpeedExecute.visit(Robot)");
        if (canExecute) {
            Speed newSpeed = element.getSpeed();
            newSpeed.setMagnitude(newSpeed.getMagnitude() + magnitudeDelta);
            element.setSpeed(newSpeed);
            result.pushMessage("Changed speed for " + element + ", new speed is: " + newSpeed.getMagnitude());
        } else {
            result.pushMessage("Failed to change speed for " + element);
        }
        Inspector.ret("ChangeSpeedExecute.visit");
    }
}
