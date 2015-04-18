package commands.executes;

import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;

public class ChangeDirectionExecute extends AgentCommand {
    private Direction direction;

    public ChangeDirectionExecute(ChangeDirectionTransmit parent) {
        super(parent);
        this.direction = parent.getDirection();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
        if (!canExecute) {
            result.pushDebug("irvalt 1" + element);
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setDirection(direction);
        element.setSpeed(newSpeed);
        result.pushDebug("irvalt 0" + element + " " + direction);
    }

    @Override
    public void visit(Vacuum element) {

    }
}
