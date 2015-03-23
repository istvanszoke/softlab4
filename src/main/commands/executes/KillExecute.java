package commands.executes;

import agents.*;
import commands.*;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

public class KillExecute extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("KillExecute.getFieldCommand():FieldCommand");
        Inspector.ret("KillExecute.getFieldCommand thrown NoFiledCommandException");
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier)
    {
        Inspector.call("KillExecute.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("KillExecute.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("KillExecute.visit(Robot)");
        if (canExecute) {
            element.kill();
            result.pushMessage("Killed " + element);
        } else {
            result.pushMessage("Could not kill " + element);
        }
        Inspector.ret("KillExecute.visit");
    }
}
