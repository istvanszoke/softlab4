package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

/* Ezen osztály példányai alkotják a pálya legnagyobb részét: minden olyan cella, amely nem a célvonal
 * része, illetve nem a pálya szélét alkotja ilyen típusú. A Field osztály által implementált tulajdonságok
 * mellett A cellán átmenõ parancsokat módosíthatja a rajta található Buffokal, majd a módosított parancsokat
 * továbbítja a rajta álló Agent felé.
 */
public class FieldCell extends Field {
    /*
     * Konstruktor.
     * Az õsosztály konstruktorát hívja meg.
     * @param distanceFromGoal - A céltól való távolság.
     */
    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    /*
     * FieldVisitor fogadása.
     * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
     * @param visitor - A visitor.
     */
    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }
    
    /*
     * FieldCommand fogadása.
     * A FieldCellen lévõ minden egyes buff módosíthatja a commandot, hogy az késõbb érvényesüljon az Agenten.
     * @param command - A származtatott FieldVisitor
     */
    @Override
    public void accept(FieldCommand command) {
        for (Buff b : buffs) {
            command.accept(b);
        }
        
        
        command.visit(this);
        
        /* Lekéri a commandhoz tartozó AgentCommandot, ami lehet Transmit vagy Execute típusú.
         * Az is lehet, hogy nem kapunk új Commandot, ezesetben a jelenlegi command Execute típusú volt 
         * és az elõzõ lépésekben ténylegesen változtatott az a FieldCell állapotán.*/
        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
