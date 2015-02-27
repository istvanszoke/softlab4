package commands.executes;

import agents.Robot;
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
            result.pushMessage("Killed " + element);
        } else {
            result.pushMessage("Could not kill " + element);
        }
    }
}
