package commands.transmits;

import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.ChangeSpeedExecute;
import commands.queries.ChangeSpeedQuery;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

public class ChangeSpeedTransmit extends FieldCommand {
    private int magnitudeDelta;

    public ChangeSpeedTransmit(ChangeSpeedQuery parent) {
        super(parent);
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
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
