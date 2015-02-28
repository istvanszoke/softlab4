package commands.transmits;

import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.ChangeDirectionExecute;
import commands.queries.ChangeDirectionQuery;
import field.Direction;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

public class ChangeDirectionTransmit extends FieldCommand {
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ChangeDirectionTransmit(ChangeDirectionQuery parent) {
        super(parent.getResult(), parent.canExecute());
        this.direction = parent.getDirection();
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
