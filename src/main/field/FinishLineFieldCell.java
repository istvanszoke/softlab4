package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

/*
 * Reprezent�l egy C�lcell�t.
 * A c�lt jelz� mez�t�pus, a k�r�k nyomonk�vet�sekor haszn�lt mez�. Viselked�se egy�bk�nt megegyezik
 * a FieldCell�vel.
 */
public class FinishLineFieldCell extends Field {
    /*
     * Konstruktor.
     * Az �soszt�ly konstruktor�t h�vja meg.
     * @param distanceFromGoal - A c�lt�l val� t�vols�g.
     */
    public FinishLineFieldCell() {
        super(0);
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

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
