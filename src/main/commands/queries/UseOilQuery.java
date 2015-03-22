package commands.queries;

import agents.*;
import commands.*;
import commands.executes.UseOilExecute;
import inspector.Inspector;

public class UseOilQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("UseOilQuery.getFieldCommand():FieldCommand");
        UseOilExecute tmp = new UseOilExecute(this);
        Inspector.ret("UseOilQuery.getFieldCommand");
        return tmp;
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("UseOilQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("UseOilQuery.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("UseOilQuery.visit(Robot)");
        canExecute = element.useOil();

        if (canExecute) {
            result.pushMessage(element + " has oil in its inventory.");
        } else {
            result.pushMessage(element + " has run out of oil.");
        }
        Inspector.ret("UseOilQuery.visit");
    }
}
