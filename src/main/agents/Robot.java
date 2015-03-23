package agents;

import java.util.ArrayList;

import buff.*;
import commands.AgentCommand;
import commands.NoFieldCommandException;
import inspector.Inspector;

public class Robot extends Agent {
    private ArrayList<Buff> buffs;
    private Inventory<Sticky> stickyInventory;
    private Inventory<Oil> oilInventory;

    public Robot() {
        Inspector.call("Robot.Robot()");
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
        Inspector.ret("Robot.Robot");
    }

    public void addSticky() {
        Inspector.call("Robot.addSticky()");
        stickyInventory.addItem(new Sticky());
        Inspector.ret("Robot.addSticky");
    }

    public void addOil() {
        Inspector.call("Robot.addOil()");
        oilInventory.addItem(new Oil());
        Inspector.ret("Robot.addOil");
    }

    public boolean useSticky() {
        Inspector.call("Robot.useSticky():boolean");
        boolean result = stickyInventory.useItem();
        Inspector.ret("Robot.useSticky");
        return result;
    }

    public boolean useOil() {
        Inspector.call("Robot.useOil()");
        boolean result = oilInventory.useItem();
        Inspector.ret("Robot.useOil");
        return result;
    }

    @Override
    public void accept(AgentVisitor visitor) {
        Inspector.call("Robot.accept(AgentVisitor)");
        visitor.visit(this);
        Inspector.ret("Robot.accpet");
    }

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
