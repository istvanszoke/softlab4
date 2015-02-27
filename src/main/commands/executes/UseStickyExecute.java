package commands.executes;

import buff.Sticky;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import feedback.Result;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

public class UseStickyExecute extends FieldCommand {
    public UseStickyExecute(Result result, boolean canExecute) {
        super(result, canExecute);
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
            element.placeBuff(new Sticky());
            result.pushMessage("Placed sticky on " + element);
        }else {
            result.pushMessage("Could not place sticky on " + element);
        }
    }

    @Override
    public void visit(EmptyFieldCell element) {}

    @Override
    public void visit(FinishLineFieldCell element) {
        if (canExecute) {
            element.placeBuff(new Sticky());
            result.pushMessage("Placed sticky on " + element);
        } else {
            result.pushMessage("Could not place sticky on " + element);
        }
    }
}
