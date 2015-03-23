package agents;

import java.util.ArrayList;

import buff.*;
import commands.AgentCommand;
import commands.NoFieldCommandException;
import inspector.Inspector;

/**
 * Egy pályán lévő robotot jelképező osztály.
 * Az ősosztályban megvalósított tulajdonságokon kívül rendelkezik Sticky és Oil Inventorykkal, 
 * amelyeket a Robot tetszőlegesen használhat, ha azok nem üresek.
 */
public class Robot extends Agent {
    /**
     * Az ágensre éppen ható buffok gyűjteménye.
     */
    private ArrayList<Buff> buffs;
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
        Inspector.call("Robot.Robot()");
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
        Inspector.ret("Robot.Robot");
    }

    /**
     * Új Sticky Buff felvétele a tárolóba.
     */
    public void addSticky() {
        Inspector.call("Robot.addSticky()");
        stickyInventory.addItem(new Sticky());
        Inspector.ret("Robot.addSticky");
    }

    /**
     * Új Oil Buff felvétele a tárolóba.
     */    
    public void addOil() {
        Inspector.call("Robot.addOil()");
        oilInventory.addItem(new Oil());
        Inspector.ret("Robot.addOil");
    }

    /**
     * Egy Sticky Buff felhasználása.
     * @return - felhasználás sikeressége
     */
    public boolean useSticky() {
        Inspector.call("Robot.useSticky():boolean");
        boolean result = stickyInventory.useItem();
        Inspector.ret("Robot.useSticky");
        return result;
    }

    /**
     * Egy Oil Buff felhasználása.
     * @return - felhasználás sikeressége
     */
    public boolean useOil() {
        Inspector.call("Robot.useOil()");
        boolean result = oilInventory.useItem();
        Inspector.ret("Robot.useOil");
        return result;
    }

   /**
    * AgentVisitor fogadása.
    * Nem módosít semmit a visitoron. Feltétel nélkül hagyja, hogy visiteljék.
    * @param visitor - A visitor.
    */    
    @Override
    public void accept(AgentVisitor visitor) {
        Inspector.call("Robot.accept(AgentVisitor)");
        visitor.visit(this);
        Inspector.ret("Robot.accpet");
    }

    /**
     * AgentCommand fogadása.
     * A Robotra érvényes minden egyes buff módosíthatja a commandot, hogy az később érvényesüljon az Agenten.
     * @param command - A származtatott AgentVisitor
     */
    @Override
    public void accept(AgentCommand command) {
        Inspector.call("Robot.accept(AgentCommand)");
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            field.accept(command.getFieldCommand());
        } catch (NoFieldCommandException ignored) {

        }
        Inspector.ret("Robot.accept");
    }
}
