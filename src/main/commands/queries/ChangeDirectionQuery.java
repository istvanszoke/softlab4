package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;

public class ChangeDirectionQuery extends AgentCommand {
    private Direction direction;

    public ChangeDirectionQuery(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new ChangeDirectionTransmit(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) { }
}
