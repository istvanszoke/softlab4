package agents;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class Robot extends Agent {
    private List<Buff> buffs;
    private Inventory<Sticky> stickyInventory;
    private Inventory<Oil> oilInventory;
    private static int instanceCount = 0;
    private int robotId;

    public static void resetInstanceCount() {
        instanceCount = 0;
    }

    public static void writeStaticParams(ObjectOutputStream oos) throws IOException {
        Integer wrapOutput = instanceCount;
        oos.writeObject(wrapOutput);
    }

    public static void readStaticParams(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Integer wrapInput = (Integer)ois.readObject();
        instanceCount = wrapInput;
    }

    public Robot() {
        buffs = new ArrayList<Buff>();
        stickyInventory = new Inventory<Sticky>();
        oilInventory = new Inventory<Oil>();
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
        if (getSpeed().getMagnitude() < agent.getSpeed().getMagnitude()) {
            KillExecute kill = new KillExecute();
            this.accept(kill);
            Logger.log(kill.getResult());
            agent.setSpeed(Speed.add(getSpeed(), agent.getSpeed()));
            return agent;
        }
        KillExecute kill = new KillExecute();
        agent.accept(kill);
        Logger.log(kill.getResult());
        setSpeed(Speed.add(getSpeed(), agent.getSpeed()));
        return this;
    }

    public int getRobotId() {
        return robotId;
    }

    @Override
    public String toString() {
        return "" + getRobotId();
    }

}
