package field;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;

/* Reprezentálja a pálya nem érvényes részét.
 * Az EmptyFieldCell osztály példányai a pálya azon részeit tárolja, amelyre lépve a Robotok kiesnek a
 * játékból. Az õ felelõssége a robotnak elküldeni azt a parancsot, ami ezt a hatást elõidézi (KillExecute).
 */
public class EmptyFieldCell extends Field {
    /*
     * Konstruktor.
     * Az õsosztály konstruktorát hívja meg.
     * @param distanceFromGoal - A céltól való távolság.
     */
    public EmptyFieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    /*
     * Agent cellára lép.
     * Lekezeli azt az estet, amikor egy Agent a cellára lép: beállítja a megfelelõ refernciákat
     * (mind az Agentben, mint önmagában). Megöli az Agentet.
     * @param agent - A cellára lépõ Agent.
     */
    public void onEnter(Agent agent) {
        agent.setField(this);
        this.agent = agent;
        agent.accept(new KillExecute());
    }
    
    /*
     * Irány keresés.
     * Innen senki sem lép sehova ezért nincs mit keresni.
     * @param speed - Sebesség
     * @return - Önmaga.
     */
    @Override
    protected Field searchGoal(Speed speed) {
        return this;
    }
    
    /*
     * FieldVisitor fogadása.
     * @param visitor - Visitor
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

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
