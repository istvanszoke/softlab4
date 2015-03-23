package commands.executes;

import buff.Oil;
import commands.*;
import commands.queries.UseOilQuery;
import field.*;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

public class UseOilExecute extends FieldCommand {
    public UseOilExecute(UseOilQuery parent) {
        super(parent);
        Inspector.call("UseOilExecute.UseOilExecute(UseOilQuery)");
        Inspector.ret("UseOilExecute.UseOilExecute");
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("UseOilExecute.getAgentCommand():AgentCommand");
        Inspector.ret("UseOilExecute.getAgentCommand thrown NoAgentCommandException");
        throw new NoAgentCommandException();
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("UseOilExecute.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("UseOilExecute.accept");
    }

    @Override
    public void visit(FieldCell element) {
        Inspector.call("UseOilExecute.visit(FieldCell)");
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushMessage("Placed oil on " + element);
        } else {
            result.pushMessage("Could not place oil on " + element);
        }
        Inspector.ret("UseOilExecute.visit");
    }

    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("UseOilExecute.visit(EmptyFieldCell)");
        Inspector.ret("UseOilExecute.visit");
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        Inspector.call("UseOilExecute.visit(EmptyFieldCell)");
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushMessage("Placed oil on " + element);
        } else {
            result.pushMessage("Could not place oil on " + element);
        }
        Inspector.ret("UseOilExecute.visit");
    }
}
