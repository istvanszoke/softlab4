package commands.queries;

import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
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

    @Override
    public void visit(Vacuum element) {

    }
}
