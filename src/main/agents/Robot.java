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
import feedback.Logger;
import feedback.Result;

public class Robot extends Agent {
    private List<Buff> buffs;
    private Inventory<Sticky> stickyInventory;
    private Inventory<Oil> oilInventory;
    private static int instanceCount = 0;
    private int robotId;

    public static void resetInstanceCount() {
        instanceCount = 0;
    }

    public Robot() {
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
        oilInventory.addItem(Oil.createInactive());
        oilInventory.addItem(Oil.createInactive());
        stickyInventory.addItem(new Sticky());
        stickyInventory.addItem(new Sticky());
        ++instanceCount;
        robotId = instanceCount;
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

    public int getOilCount() { return oilInventory.getItemCount(); }

    public int getStickyCount() { return stickyInventory.getItemCount(); }

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

    @Override
    public boolean onCauseCollision(Agent agent) {
        return true;
    }

    @Override
    public Agent collide(Agent agent) {
        Speed ownSpeed = getSpeed();
        Speed otherSpeed = agent.getSpeed();
        Speed postCollisionSpeed = Speed.add(ownSpeed, otherSpeed);

        KillExecute kill = new KillExecute();
        Result killResult = kill.getResult();
        if (ownSpeed.getMagnitude() < otherSpeed.getMagnitude()) {
            this.accept(kill);
            Logger.log(killResult);
            agent.setSpeed(postCollisionSpeed);
            return agent;
        }
        agent.accept(kill);
        Logger.log(killResult);
        setSpeed(postCollisionSpeed);
        return this;
    }

    public int getRobotId() {
        return robotId;
    }

    @Override
    public String toString() {
        return "" + getRobotId();
    }

    @Override
    public void setSpeed(Speed speed) {
        if (Math.abs(this.speed.getMagnitude()) <= 8) {
            this.speed = speed;
        } else {
            if (speed.getMagnitude() < 0) {
                speed.setMagnitude(0);
            } else {
                speed.setMagnitude(8);
            }
            this.speed = speed;
        }
    }

}
