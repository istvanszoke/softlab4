package commands.executes;

import buff.Oil;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.queries.UseOilQuery;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;
import field.FinishLineFieldCell;

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
        visitCommon(element);
    }

    @Override
    public void visit(EmptyFieldCell element) {}

    @Override
    public void visit(FinishLineFieldCell element) {
        visitCommon(element);
    }

    private void visitCommon(Field element) {
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushNormal("olajlerak 0");
        } else {
            result.pushNormal("olajlerak 1");
        }
    }
}
