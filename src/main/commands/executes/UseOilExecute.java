package commands.executes;

import buff.Oil;
import commands.*;
import commands.queries.UseOilQuery;
import feedback.Result;
import field.*;

public class UseOilExecute extends FieldCommand {
    public UseOilExecute(UseOilQuery parent) {
        super(parent);
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        throw new NoAgentCommandException();
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(FieldCell element) {
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushMessage("Placed oil on " + element);
        } else {
            result.pushMessage("Could not place oil on " + element);
        }
    }

    @Override
    public void visit(EmptyFieldCell element) {}

    @Override
    public void visit(FinishLineFieldCell element) {
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushMessage("Placed oil on " + element);
        } else {
            result.pushMessage("Could not place oil on " + element);
        }
    }
}
