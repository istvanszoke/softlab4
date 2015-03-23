package commands;

import agents.AgentVisitor;
import inspector.Inspector;

public abstract class AgentCommand extends Command implements AgentVisitor {
    protected AgentCommand()
    {
        Inspector.call("AgentCommand.AgentCommand()");
        Inspector.ret("AgentCommand.AgentCommand");
    }

    protected AgentCommand(Command parent)
    {
        super(parent);
        Inspector.call("AgentCommand.AgentCommand(Command)");
        Inspector.ret("AgentCommand.AgentCommand");
    }

    public abstract FieldCommand getFieldCommand() throws NoFieldCommandException;

    public abstract void accept(AgentCommandVisitor modifier);
}
