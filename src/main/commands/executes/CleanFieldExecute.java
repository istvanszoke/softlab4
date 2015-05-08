package commands.executes;

import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

public class CleanFieldExecute extends FieldCommand {
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        throw new NoAgentCommandException();
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(FieldCell element) { }

    @Override
    public void visit(EmptyFieldCell element) { }

    @Override
    public void visit(FinishLineFieldCell element) { }
}
