package commands.executes;

import buff.Sticky;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.queries.UseStickyQuery;
import field.EmptyFieldCell;
import field.Field;
import field.FieldCell;
import field.FinishLineFieldCell;

public class UseStickyExecute extends FieldCommand {
    public UseStickyExecute(UseStickyQuery parent) {
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
            element.placeBuff(new Sticky());
            result.pushNormal("ragacslerak 0");
        } else {
            result.pushNormal("ragacslerak 1");
        }
    }
}
