package commands;

import field.FieldVisitor;

public abstract class FieldCommand extends Command implements FieldVisitor {
    public FieldCommand() { }

    public FieldCommand(Command parent) {
        super(parent);
    }

    public abstract AgentCommand getAgentCommand() throws NoAgentCommandException;

    public abstract void accept(FieldCommandVisitor modifier);
}
