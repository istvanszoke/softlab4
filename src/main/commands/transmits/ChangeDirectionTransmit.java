package commands.transmits;

import commands.*;
import commands.executes.ChangeDirectionExecute;
import commands.queries.ChangeDirectionQuery;
import field.*;
import inspector.Inspector;

public class ChangeDirectionTransmit extends FieldCommand {
    private Direction direction;

    public ChangeDirectionTransmit(ChangeDirectionQuery parent) {
        super(parent);
        Inspector.call("ChangeDirectionTransmit:ChangeDirectionTransmit(ChangeDirectionQuery)");
        this.direction = parent.getDirection();
        Inspector.ret("ChangeDirectionTransmit:ChangeDirectionTransmit");
    }

    public Direction getDirection() {
        Inspector.call("ChangeDirectionTransmit.getDirection():Direction");
        Inspector.ret("ChangeDirectionTransmit.getDirection");
        return direction;
    }

    public void setDirection(Direction direction) {
        Inspector.call("ChangeDirectionTransmit.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionTransmit.setDirection");
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("ChangeDirectionTransmit.getAgentCommand():AgentCommand");
        ChangeDirectionExecute tmp = new ChangeDirectionExecute(this);
        Inspector.ret("ChangeDirectionTransmit.getAgentCommand");
        return tmp;
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("ChangeDirectionTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionTransmit.accept");
    }

    @Override
    public void visit(FieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(FieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }

    @Override
    public void visit(EmptyFieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(EmptyFieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }

    @Override
    public void visit(FinishLineFieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(FinishLineFieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }
}
