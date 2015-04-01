package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.executes.UseOilExecute;

public class UseOilQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseOilExecute(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        canExecute = element.useOil();

        if (canExecute) {
            result.pushDebug(element + " has oil in its inventory.");
        } else {
            result.pushDebug(element + " has run out of oil.");
        }
    }
}
