package commands.queries;

import agents.*;
import commands.*;
import commands.executes.UseOilExecute;

public class UseOilQuery extends AgentCommand {
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseOilExecute(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        canExecute = element.useOil();

        if (canExecute) {
            result.pushMessage(element + " has oil in its inventory.");
        } else {
            result.pushMessage(element + " has run out of oil.");
        }
    }
}
