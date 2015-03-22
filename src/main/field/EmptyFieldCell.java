package field;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;

/* Reprezent�lja a p�lya nem �rv�nyes r�sz�t.
 * Az EmptyFieldCell oszt�ly p�ld�nyai a p�lya azon r�szeit t�rolja, amelyre l�pve a Robotok kiesnek a
 * j�t�kb�l. Az � felel�ss�ge a robotnak elk�ldeni azt a parancsot, ami ezt a hat�st el�id�zi (KillExecute).
 */
public class EmptyFieldCell extends Field {
    /*
     * Konstruktor.
     * Az �soszt�ly konstruktor�t h�vja meg.
     * @param distanceFromGoal - A c�lt�l val� t�vols�g.
     */
    public EmptyFieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    /*
     * Agent cell�ra l�p.
     * Lekezeli azt az estet, amikor egy Agent a cell�ra l�p: be�ll�tja a megfelel� refernci�kat
     * (mind az Agentben, mint �nmag�ban). Meg�li az Agentet.
     * @param agent - A cell�ra l�p� Agent.
     */
    public void onEnter(Agent agent) {
        agent.setField(this);
        this.agent = agent;
        agent.accept(new KillExecute());
    }
    
    /*
     * Ir�ny keres�s.
     * Innen senki sem l�p sehova ez�rt nincs mit keresni.
     * @param speed - Sebess�g
     * @return - �nmaga.
     */
    @Override
    protected Field searchGoal(Speed speed) {
        return this;
    }
    
    /*
     * FieldVisitor fogad�sa.
     * @param visitor - Visitor
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
