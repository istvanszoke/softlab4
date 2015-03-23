package commands.queries;

import agents.*;
import commands.*;
import commands.executes.UseStickyExecute;
import inspector.Inspector;

public class UseStickyQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("UseStickyQuery.getFieldCommand()");
        UseStickyExecute tmp = new UseStickyExecute(this);
        Inspector.ret("UseStickyQuery.getFieldCommand");
        return tmp;
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("UseStickyQuery.accept(AgentCommandVisitor");
        modifier.visit(this);
        Inspector.ret("UseStickyQuery.accpet");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("UseStickyQuery.visit(Robot)");
        canExecute = element.useSticky();

        if (canExecute) {
            result.pushMessage(element + " has sticky in its inventory.");
        } else {
            result.pushMessage(element + " has run out of sticky.");
        }
        Inspector.ret("UseStickyQuery.visit");
    }
}
