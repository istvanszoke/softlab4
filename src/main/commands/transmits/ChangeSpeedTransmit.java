package commands.transmits;

import commands.*;
import commands.executes.ChangeSpeedExecute;
import commands.queries.ChangeSpeedQuery;
import field.*;

public class ChangeSpeedTransmit extends FieldCommand {
    private int magnitudeDelta;

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    public ChangeSpeedTransmit(ChangeSpeedQuery parent) {
        super(parent.getResult(), parent.canExecute());
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new ChangeSpeedExecute(this);
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
