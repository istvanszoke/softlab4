package agents;

import java.util.ArrayList;
import java.util.List;

import buff.Buff;
import buff.Inventory;
import buff.Oil;
import buff.Sticky;
import commands.AgentCommand;
import commands.NoFieldCommandException;
import commands.executes.KillExecute;

/**
 * Egy pályán lévő robotot jelképező osztály.
 * Az ősosztályban megvalósított tulajdonságokon kívül rendelkezik Sticky és Oil Inventorykkal,
 * amelyeket a Robot tetszőlegesen használhat, ha azok nem üresek.
 */
public class Vacuum extends Agent {
    /**
     * Konstruktor.
     */
    public Vacuum() {

    }

    /**
     * AgentVisitor fogadása.
     * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
     *
     * @param visitor - A visitor.
     */
    @Override
    public void accept(AgentVisitor visitor) {

    }

    /**
     * AgentCommand fogadása.
     * A Robotra érvényes minden egyes buff módosíthatja a commandot, hogy az később érvényesüljon az Agenten.
     *
     * @param command - A származtatott AgentVisitor
     */
    @Override
    public void accept(AgentCommand command) {

    }

    /**
     * Ütközés okozása
     * Itt fog irány váltani, ha vacuum-mal találkozik a vacuum
     * Vacuum nem okoz ütközést, ezért false-al tér vissza mindig.
     *
     * @param agent - az Agent, amibe ütközne
     * @return Okoz-e ütközést
     */
    @Override
    public boolean onCauseCollision(Agent agent) {
        return false;
    }


    /**
     * Ütközés
     * Mivel a vaccum nem okoz ütközés, ezért ez a metódus soha nem fog meghívódni.
     *
     * @param agent - Az Agent, amibe ütközne
     * @return null
     */
    @Override
    public Agent collide(Agent agent) {
        return null;
    }
}
