package commands;

import feedback.Result;
import field.FieldVisitor;

public abstract class FieldCommand extends Command implements FieldVisitor {
    public FieldCommand() {}

    public FieldCommand(Result result, boolean canExecute) {
        super(result, canExecute);
    }

    public abstract AgentCommand getAgentCommand() throws NoAgentCommandException;
    public abstract void accept(FieldCommandVisitor modifier);
}
