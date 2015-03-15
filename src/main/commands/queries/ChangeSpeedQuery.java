package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;

public class ChangeSpeedQuery extends AgentCommand {
    private int magnitudeDelta;

    public ChangeSpeedQuery(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new ChangeSpeedTransmit(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) { }
}
