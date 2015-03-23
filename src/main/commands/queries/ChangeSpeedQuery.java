package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

public class ChangeSpeedQuery extends AgentCommand {
    private int magnitudeDelta;

    public ChangeSpeedQuery(int magnitudeDelta) {
        Inspector.call("ChangeSpeedQuery.ChangeSpeedQuery(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedQuery.ChangeSpeedQuery");
    }

    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedQuery.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedQuery.getMagnitudeDelta");
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedQuery.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedQuery.setMagnitudeDelta");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeSpeedQuery.getFieldCommand():FieldCommand");
        ChangeSpeedTransmit tmp = new ChangeSpeedTransmit(this);
        Inspector.ret("ChangeSpeedQuery.getFieldCommand");
        return tmp;
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeSpeedQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedQuery.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeSpeedQuery.visit(Robot)");
        Inspector.ret("ChangeSpeedQuery.visit");
    }
}
