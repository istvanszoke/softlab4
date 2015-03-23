package commands;

import field.FieldVisitor;
import inspector.Inspector;

public abstract class FieldCommand extends Command implements FieldVisitor {
    protected FieldCommand()
    {
        Inspector.call("FieldCommand.FieldCommand()");
        Inspector.ret("FieldCommand.FieldCommand");
    }

    protected FieldCommand(Command parent) {
        super(parent);
        Inspector.call("FieldCommand.FieldCommand(Command)");
        Inspector.ret("FieldCommand.FieldCommand");
    }

    public abstract AgentCommand getAgentCommand() throws NoAgentCommandException;

    public abstract void accept(FieldCommandVisitor modifier);
}
