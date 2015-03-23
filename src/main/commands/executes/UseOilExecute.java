package commands.executes;

import buff.Oil;
import commands.*;
import commands.queries.UseOilQuery;
import field.*;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

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
        Inspector.call("UseOilExecute.UseOilExecute(UseOilQuery)");
        Inspector.ret("UseOilExecute.UseOilExecute");
    }

    /**
     * Egy Ágens parancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("UseOilExecute.getAgentCommand():AgentCommand");
        Inspector.ret("UseOilExecute.getAgentCommand thrown NoAgentCommandException");
        throw new NoAgentCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("UseOilExecute.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("UseOilExecute.accept");
    }

    /**
     * Egy pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
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

    /**
     * Egy üres pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("UseOilExecute.visit(EmptyFieldCell)");
        Inspector.ret("UseOilExecute.visit");
    }

    /**
     * Egy célvonali pályamező maipulálásához interfész
     * @param element - A manipulálandó mező referenciája
     */
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
