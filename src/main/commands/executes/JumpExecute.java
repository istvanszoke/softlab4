package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.JumpTransmit;
import field.Displacement;
import inspector.Inspector;

/**
 * Ugrás kivitelezés egy Ágensen
 */
public class JumpExecute extends AgentCommand {
    /**
     * Az ugrás elmozdulása
     */
    private Displacement displacement;

    /**
     * Osztálykonstruktor
     * A kérésátvitelből generálódik
     * @param parent - A felhasználtt kérésátvitel
     */
    public JumpExecute(JumpTransmit parent) {
        super(parent);
        Inspector.call("JumpExecute.JumpExecute(JumpTransmit)");
        this.displacement = parent.getDisplacement();
        Inspector.ret("JumpExecute.JumpExecute");
    }

    /**
     * Elmozdulást lekérő függvény
     * @return - Elmozdulás
     */
    public Displacement getDisplacement() {
        Inspector.call("JumpExecute.getDisplacement():Displacement");
        Inspector.ret("JumpExecute.getDisplacement");
        return displacement;
    }

    /**
     * Új elmozdulást beállító függvény
     * @param displacement - Az új elmozdulás
     */
    public void setDisplacement(Displacement displacement) {
        Inspector.call("JumpExecute.setDisplacement(Displacement)");
        this.displacement = displacement;
        Inspector.ret("JumpExecute.setDispalcement");
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("JumpExecute.getFieldCommand():FieldCommand");
        Inspector.ret("JumpExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("JumpExecute.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("JumpExecute.accept");
    }

    /**
     * Egy Robot maipulálásához interfész
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("JumpExecute.visit(Robot)");
        if (canExecute) {
            displacement.getStart().onExit();
            displacement.getGoal().onEnter(element);
            result.pushMessage("Jumping " + element + " " +
                               "from " + displacement.getStart() +
                               " to " + displacement.getGoal());
        } else {
            result.pushMessage("Cannot execute jump for " + element);
        }
        Inspector.ret("JumpExecute.visit");
    }
}
