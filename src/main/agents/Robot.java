package agents;

import buff.Buff;
import buff.Inventory;
import buff.Oil;
import buff.Sticky;
import commands.AgentCommand;
import commands.NoFieldCommandException;

import java.util.ArrayList;

public class Robot extends Agent {
    ArrayList<Buff> buffs = new ArrayList<Buff>();
    Inventory<Sticky> stickyInventory = new Inventory<Sticky>();
    Inventory<Oil> oilInventory = new Inventory<Oil>();

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
        } catch(NoFieldCommandException ignored) {

        }
    }
}
