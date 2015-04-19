package commands.queries;

import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;

public class CleanFieldQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {

    }

    @Override
    public void visit(Robot element) {
        canExecute = false;
    //    result.pushDebug("Robot can not clean up Field!");
        result.pushNormal("vacuumtakarit 1 " + element);
    }

    @Override
    public void visit(Vacuum element) {
        canExecute = true;
        if (!element.tryToClean()) {
            result.pushNormal("Nothing to clean on current field");
        }
    }
}
