package commands.transmits;

import commands.*;
import commands.executes.ChangeSpeedExecute;
import commands.queries.ChangeSpeedQuery;
import field.*;
import inspector.Inspector;

public class ChangeSpeedTransmit extends FieldCommand {
    private int magnitudeDelta;

    public ChangeSpeedTransmit(ChangeSpeedQuery parent) {
        super(parent);
        Inspector.call("ChangeSpeedTransmit.ChangeSpeedTransmit(ChangeSpeedQuery)");
        this.magnitudeDelta = parent.getMagnitudeDelta();
        Inspector.ret("ChangeSpeedTransmit.ChangeSpeedTransmit");
    }

    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedTransmit.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedTransmit.getMagnitudeDelta");
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedTransmit.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedTransmit.setMagnitudeDelta(int)");
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("ChangeSpeedTransmit.getAgentCommand():AgentCommand");
        ChangeSpeedExecute tmp = new ChangeSpeedExecute(this);
        Inspector.ret("ChangeSpeedTransmit.getAgentCommand");
        return tmp;
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("ChangeSpeedTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedTransmit.accept");
    }

    @Override
    public void visit(FieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(FieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }

    @Override
    public void visit(EmptyFieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(EmptyFieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }

    @Override
    public void visit(FinishLineFieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(FinishLineFieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }
}
