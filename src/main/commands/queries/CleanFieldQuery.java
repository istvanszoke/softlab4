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
    }

    @Override
    public void visit(Vacuum element) {
        canExecute = true;
        element.tryToClean();
    }
}
