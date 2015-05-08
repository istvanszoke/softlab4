package commands.queries;

import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;

public class CleanFieldQuery extends AgentCommand {
    private static final long serialVersionUID = -8165111680281711995L;

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
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
