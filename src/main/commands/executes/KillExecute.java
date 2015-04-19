package commands.executes;

import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;

public class KillExecute extends AgentCommand {
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
        if (canExecute) {
            element.kill();
            result.pushDebug("Killed " + element);
        } else {
            result.pushDebug("Could not kill " + element);
        }
    }

    @Override
    public void visit(Vacuum element) {

    }
}
