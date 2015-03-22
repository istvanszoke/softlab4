package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

/* Ezen oszt�ly p�ld�nyai alkotj�k a p�lya legnagyobb r�sz�t: minden olyan cella, amely nem a c�lvonal
 * r�sze, illetve nem a p�lya sz�l�t alkotja ilyen t�pus�. A Field oszt�ly �ltal implement�lt tulajdons�gok
 * mellett A cell�n �tmen� parancsokat m�dos�thatja a rajta tal�lhat� Buffokal, majd a m�dos�tott parancsokat
 * tov�bb�tja a rajta �ll� Agent fel�.
 */
public class FieldCell extends Field {
    /*
     * Konstruktor.
     * Az �soszt�ly konstruktor�t h�vja meg.
     * @param distanceFromGoal - A c�lt�l val� t�vols�g.
     */
    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    /*
     * FieldVisitor fogad�sa.
     * Nem m�dos�t semmit a visitoron. Felt�tel n�lk�l hagyja, hogy visitelj�k.
     * @param visitor - A visitor.
     */
    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }
    
    /*
     * FieldCommand fogad�sa.
     * A FieldCellen l�v� minden egyes buff m�dos�thatja a commandot, hogy az k�s�bb �rv�nyes�ljon az Agenten.
     * @param command - A sz�rmaztatott FieldVisitor
     */
    @Override
    public void accept(FieldCommand command) {
        for (Buff b : buffs) {
            command.accept(b);
        }
        
        
        command.visit(this);
        
        /* Lek�ri a commandhoz tartoz� AgentCommandot, ami lehet Transmit vagy Execute t�pus�.
         * Az is lehet, hogy nem kapunk �j Commandot, ezesetben a jelenlegi command Execute t�pus� volt 
         * �s az el�z� l�p�sekben t�nylegesen v�ltoztatott az a FieldCell �llapot�n.*/
        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
