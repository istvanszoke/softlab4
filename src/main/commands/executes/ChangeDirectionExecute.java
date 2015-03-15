package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;

public class ChangeDirectionExecute extends AgentCommand {
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ChangeDirectionExecute(ChangeDirectionTransmit parent) {
        super(parent.getResult(), parent.canExecute());
        this.direction = parent.getDirection();
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
            result.pushMessage("No direction changed for " + element);
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setDirection(direction);
        element.setSpeed(newSpeed);
        result.pushMessage("Changed direction for " + element + ", new direction is: " + direction);
    }
}
