package commands.executes;

import buff.Sticky;
import commands.*;
import commands.queries.UseStickyQuery;
import field.*;
import inspector.Inspector;

public class UseStickyExecute extends FieldCommand {
    public UseStickyExecute(UseStickyQuery parent) {
        super(parent);
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("AgentCommand.getAgentCommand():AgentCommand");
        Inspector.ret("AgentCommand.getAgentCommand thrown NoAgentCommandException");
        throw new NoAgentCommandException();
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("AgentCommand.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("AgentCommand.accept");
    }

    @Override
    public void visit(FieldCell element) {
        Inspector.call("AgentCommand.visit(FieldCell)");
        if (canExecute) {
            element.placeBuff(new Sticky());
            result.pushMessage("Placed sticky on " + element);
        } else {
            result.pushMessage("Could not place sticky on " + element);
        }
        Inspector.ret("AgentCommand.visit");
    }

    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("AgentCommand.visit(EmptyFieldCell");
        Inspector.ret("AgentCommand.visit");
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        Inspector.call("AgentCommand.visit(FinishLineFieldCell)");
        if (canExecute) {
            element.placeBuff(new Sticky());
            result.pushMessage("Placed sticky on " + element);
        } else {
            result.pushMessage("Could not place sticky on " + element);
        }
        Inspector.ret("AgentCommand.visit");
    }
}
