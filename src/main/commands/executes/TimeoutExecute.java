package commands.executes;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;

public class TimeoutExecute extends AgentCommand {
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
            element.timeOut();
            result.pushMessage("Timed out robot: " + element);
        } else {
            result.pushMessage("Could not timeout: " + element);
        }
    }
}
