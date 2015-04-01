package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

/**
 * Egy célmezőt reprezentál.
 * A célt jelző mezőtípus, a körök nyomonkövetésekor használt mező. Viselkedése egyébként megegyezik
 * a FieldCellével.
 */
public class FinishLineFieldCell extends Field {
    /**
     * Konstruktor.
     * Az ősosztály konstruktorát hívja meg.
     * @param distanceFromGoal - A céltól való távolság.
     */
    public FinishLineFieldCell() {
        super(0);
    }
 
   /**
    * FieldVisitor fogadása.
    * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
    * @param visitor - A visitor.
    */
    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * FieldCommand fogadása.
     * A FieldCellen lévő minden egyes buff módosíthatja a commandot, hogy az később érvényesüljon az Agenten.
     * @param command - A származtatott FieldVisitor
     */
    @Override
    public void accept(FieldCommand command) {
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
