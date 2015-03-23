package commands.executes;

import buff.Sticky;
import commands.*;
import commands.queries.UseStickyQuery;
import field.*;
import inspector.Inspector;

/**
 * Ragacs lehelyezés kivitelezése egy mezőn
 */
public class UseStickyExecute extends FieldCommand {
    public UseStickyExecute(UseStickyQuery parent) {
        super(parent);
    }

    /**
     * Egy Ágens parancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("AgentCommand.getAgentCommand():AgentCommand");
        Inspector.ret("AgentCommand.getAgentCommand thrown NoAgentCommandException");
        throw new NoAgentCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("AgentCommand.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("AgentCommand.accept");
    }


    /**
     * Egy pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
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

    /**
     * Egy üres pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("AgentCommand.visit(EmptyFieldCell");
        Inspector.ret("AgentCommand.visit");
    }

    /**
     * Egy célvonali pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
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
