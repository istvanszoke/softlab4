package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;
import inspector.Inspector;

public class ChangeDirectionQuery extends AgentCommand {
    private Direction direction;

    public ChangeDirectionQuery(Direction direction)
    {
        Inspector.call("ChangeDirectionQuery.ChangeDirectionQuery(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionQuery.ChangeDirectionQuery");
    }

    public Direction getDirection()
    {
        Inspector.call("ChangeDirectionQuery.getDirection():Direction");
        Inspector.ret("ChangeDirectionQuery.getDirection");
        return direction;
    }

    public void setDirection(Direction direction)
    {
        Inspector.call("ChangeDirectionQuery.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionQuery.setDirection(Direction)");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeDirectionQuery.getFieldCommand():FieldCommand");
        ChangeDirectionTransmit tmp = new ChangeDirectionTransmit(this);
        Inspector.ret("ChangeDirectionQuery.getFieldCommand");
        return tmp;
    }

    @Override
    public void accept(AgentCommandVisitor modifier)
    {
        Inspector.call("ChangeDirectionQuery.accept(AgendCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionQuery.accept");
    }

    @Override
    public void visit(Robot element)
    {
        Inspector.call("ChangeDirectionQuery.accept(Robot)");
        Inspector.ret("ChangeDirectionQuery.accept");
    }
}
