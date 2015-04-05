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
public class Robot extends Agent {
    /**
     * Az ágensre éppen ható buffok gyűjteménye.
     */
    private List<Buff> buffs;
    /**
     * Az ágens rendelkezésére álló Sticky buffok tárolója
     */
    private Inventory<Sticky> stickyInventory;
    /**
     * Az ágens rendelkezésére álló Oil buffok tárolója
     */
    private Inventory<Oil> oilInventory;

    /**
     * Konstruktor.
     */
    public Robot() {
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
    }

    /**
     * Új Sticky Buff felvétele a tárolóba.
     */
    public void addSticky() {
        stickyInventory.addItem(new Sticky());
    }

    /**
     * Új Oil Buff felvétele a tárolóba.
     */
    public void addOil() {
        oilInventory.addItem(new Oil());
    }

    /**
     * Egy Sticky Buff felhasználása.
     *
     * @return - felhasználás sikeressége
     */
    public boolean useSticky() {
        return stickyInventory.useItem();
    }

    /**
     * Egy Oil Buff felhasználása.
     *
     * @return - felhasználás sikeressége
     */
    public boolean useOil() {
        return oilInventory.useItem();
    }

    /**
     * AgentVisitor fogadása.
     * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
     *
     * @param visitor - A visitor.
     */
    @Override
    public void accept(AgentVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * AgentCommand fogadása.
     * A Robotra érvényes minden egyes buff módosíthatja a commandot, hogy az később érvényesüljon az Agenten.
     *
     * @param command - A származtatott AgentVisitor
     */
    @Override
    public void accept(AgentCommand command) {
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            field.accept(command.getFieldCommand());
        } catch (NoFieldCommandException ignored) {

        }
    }

    @Override
    public boolean onCauseCollision(Agent agent) {
        return true;
    }

    @Override
    public Agent collide(Agent agent) {
        if (getSpeed().getMagnitude() < agent.getSpeed().getMagnitude()) {
            this.accept(new KillExecute());
            agent.setSpeed(Speed.add(getSpeed(), agent.getSpeed()));
            return agent;
        }
        agent.accept(new KillExecute());
        setSpeed(Speed.add(getSpeed(), agent.getSpeed()));
        return this;
    }
}
