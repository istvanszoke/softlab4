package commands;

import agents.AgentVisitor;

public abstract class AgentCommand extends Command implements AgentVisitor {
    protected AgentCommand() { }

    protected AgentCommand(Command parent) {
        super(parent);
    }

    public abstract FieldCommand getFieldCommand() throws NoFieldCommandException;

    public abstract void accept(AgentCommandVisitor modifier);
}
