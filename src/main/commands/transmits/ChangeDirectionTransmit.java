package commands.transmits;

import commands.*;
import commands.executes.ChangeDirectionExecute;
import commands.queries.ChangeDirectionQuery;
import field.*;

public class ChangeDirectionTransmit extends FieldCommand {
    private Direction direction;

    public ChangeDirectionTransmit(ChangeDirectionQuery parent) {
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
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new ChangeDirectionExecute(this);
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(FieldCell element) {}

    @Override
    public void visit(EmptyFieldCell element) {}

    @Override
    public void visit(FinishLineFieldCell element) {}
}
