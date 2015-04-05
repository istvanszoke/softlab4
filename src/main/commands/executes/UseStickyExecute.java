package commands.executes;

import buff.Sticky;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.queries.UseStickyQuery;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

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
     *
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        throw new NoAgentCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     *
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }


    /**
     * Egy pályamező maipulálásához interfész
     *
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(FieldCell element) {
        if (canExecute) {
            element.placeBuff(new Sticky());
            result.pushDebug("Placed sticky on " + element);
        } else {
            result.pushDebug("Could not place sticky on " + element);
        }
    }

    /**
     * Egy üres pályamező maipulálásához interfész
     *
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(EmptyFieldCell element) {}

    /**
     * Egy célvonali pályamező maipulálásához interfész
     *
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(FinishLineFieldCell element) {
        if (canExecute) {
            element.placeBuff(new Sticky());
            result.pushDebug("Placed sticky on " + element);
        } else {
            result.pushDebug("Could not place sticky on " + element);
        }
    }
}
