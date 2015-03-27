package agents;

import java.util.ArrayList;

import buff.Buff;
import buff.Inventory;
import buff.Oil;
import buff.Sticky;
import commands.AgentCommand;
import commands.NoFieldCommandException;

public class Robot extends Agent {
    private ArrayList<Buff> buffs;
    private Inventory<Sticky> stickyInventory;
    private Inventory<Oil> oilInventory;

    public Robot() {
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
    }

    public void addSticky() {
        stickyInventory.addItem(new Sticky());
    }

    public void addOil() {
        oilInventory.addItem(new Oil());
    }

    public boolean useSticky() {
        return stickyInventory.useItem();
    }

    public boolean useOil() {
        return oilInventory.useItem();
    }

    @Override
    public void accept(AgentVisitor visitor) {
        visitor.visit(this);
    }

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
}
