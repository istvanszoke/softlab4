package commands.executes;

import buff.Oil;
import commands.*;
import commands.queries.UseOilQuery;
import field.*;

/**
 * Olaj lehelyezés kivitelezése egy mezőn
 */
public class UseOilExecute extends FieldCommand {
    /**
     * Osztálykonstruktor
     * Olaj lehelyezés kérésből generálódik
     * @param parent - A lehelyezéskérés melyet felhasználunk
     */
    public UseOilExecute(UseOilQuery parent) {
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
        throw new NoAgentCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(FieldCell element) {
        if (canExecute) {
            element.placeBuff(new Oil());
            result.pushMessage("Placed oil on " + element);
        } else {
            result.pushMessage("Could not place oil on " + element);
        }
    }

    /**
     * Egy üres pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(EmptyFieldCell element) {}

    /**
     * Egy célvonali pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
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
