package commands.executes;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.JumpTransmit;
import field.Displacement;

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
        this.displacement = parent.getDisplacement();
    }

    /**
     * Elmozdulást lekérő függvény
     * @return - Elmozdulás
     */
    public Displacement getDisplacement() {
        return displacement;
    }

    /**
     * Új elmozdulást beállító függvény
     * @param displacement - Az új elmozdulás
     */
    public void setDisplacement(Displacement displacement) {
        this.displacement = displacement;
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy Robot maipulálásához interfész
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        if (canExecute) {
            displacement.getStart().onExit();
            displacement.getGoal().onEnter(element);
            result.pushMessage("Jumping " + element + " " +
                               "from " + displacement.getStart() +
                               " to " + displacement.getGoal());
        } else {
            result.pushMessage("Cannot execute jump for " + element);
        }
    }
}
