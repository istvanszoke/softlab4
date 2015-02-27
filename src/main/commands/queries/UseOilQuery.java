package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.executes.UseOilExecute;

public class UseOilQuery extends AgentCommand {
    private boolean canUse = false;

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseOilExecute(result, canUse);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        canUse = element.useOil();

        if (canUse) {
            result.pushMessage(element + " has oil in its inventory.");
        } else {
            result.pushMessage(element + " has run out of oil.");
        }
    }
}
