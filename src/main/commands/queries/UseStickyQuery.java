package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.executes.UseStickyExecute;

public class UseStickyQuery extends AgentCommand {
    private boolean canUse = false;

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseStickyExecute(result, canUse);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        canUse = element.useSticky();

        if (canUse) {
            result.pushMessage(element + " has sticky in its inventory.");
        } else {
            result.pushMessage(element + " has run out of sticky.");
        }
    }
}
