package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;
import inspector.Inspector;

public class ChangeDirectionExecute extends AgentCommand {
    private Direction direction;

    public ChangeDirectionExecute(ChangeDirectionTransmit parent) {
        super(parent);
        Inspector.call("ChangeDirectionExecute.ChangeDirectionExecute(ChangeDirectionTransmit)");
        this.direction = parent.getDirection();
        Inspector.ret("ChangeDirectionExecute.ChangeDirectionExecute");
    }

    public Direction getDirection() {
        Inspector.call("ChangeDirectionExecute.getDirection():Direction");
        Inspector.ret("ChangeDirectionExecute.getDirection");
        return direction;
    }

    public void setDirection(Direction direction) {
        Inspector.call("ChangeDirectionExecute.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionExecute.setDirection");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeDirectionExecute.getFieldCommand():FieldCommand");
        Inspector.ret("ChangeDirectionExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeDirectionExecute.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionExecute.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeDirectionExecute.visit(Robot)");
        if (!canExecute) {
            result.pushMessage("No direction changed for " + element);
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setDirection(direction);
        element.setSpeed(newSpeed);
        result.pushMessage("Changed direction for " + element + ", new direction is: " + direction);
        Inspector.ret("ChangeDirectionExecute.visit");
    }
}
