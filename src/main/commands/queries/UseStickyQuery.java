package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.executes.UseStickyExecute;

public class UseStickyQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseStickyExecute(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        canExecute = element.useSticky();

        if (canExecute) {
            result.pushDebug(element + " has sticky in its inventory.");
        } else {
            result.pushDebug(element + " has run out of sticky.");
        }
    }
}
