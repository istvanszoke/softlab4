package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import inspector.Inspector;

/**
 * Egy általános cellát jelképező osztály.
 * Ezen osztály példányai alkotják a pálya legnagyobb részét: minden olyan cella, amely nem a célvonal
 * része, illetve nem a pálya szélét alkotja ilyen típusú. A Field osztály által implementált tulajdonságok
 * mellett A cellán átmenő parancsokat módosíthatja a rajta található Buffokal, majd a módosított parancsokat
 * továbbítja a rajta álló Agent felé.
 */
public class FieldCell extends Field {
    /**
     * Konstruktor.
     * Az ősosztály konstruktorát hívja meg.
     * @param distanceFromGoal - A céltól való távolság.
     */
    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    /**
     * FieldVisitor fogadása.
     * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
     * @param visitor - A visitor.
     */
    @Override
    public void accept(FieldVisitor visitor) {
        Inspector.call("FieldCell.accept(FieldVisitor)");
        visitor.visit(this);
        Inspector.ret("FieldCell.accept");
    }
    
    /**
     * FieldCommand fogadása.
     * A FieldCellen lévő minden egyes buff módosíthatja a commandot, hogy az később érvényesüljon az Agenten.
     * @param command - A származtatott FieldVisitor
     */
    @Override
    public void accept(FieldCommand command) {
        Inspector.call("FieldCell.accept(FieldCommand)");
        for (Buff b : buffs) {
            command.accept(b);
        }
        
        
        command.visit(this);
        
        /* Lekéri a commandhoz tartozó AgentCommandot, ami lehet Transmit vagy Execute típusú.
         * Az is lehet, hogy nem kapunk új Commandot, ezesetben a jelenlegi command Execute típusú volt 
         * és az előző lépésekben ténylegesen változtatott az a FieldCell állapotán.
         */
        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
        Inspector.ret("FieldCell.accept");
    }
}
