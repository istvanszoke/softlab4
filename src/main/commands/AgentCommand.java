package commands;

import agents.AgentVisitor;
import feedback.Result;

public abstract class AgentCommand extends Command implements AgentVisitor {
    public AgentCommand() {}

    public AgentCommand(Result result, boolean canExecute) {
        super(result, canExecute);
    }

    public abstract FieldCommand getFieldCommand() throws NoFieldCommandException;
    public abstract void accept(AgentCommandVisitor modifier);
}
