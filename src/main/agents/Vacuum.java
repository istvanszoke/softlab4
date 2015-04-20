package agents;

import buff.Buff;

import commands.AgentCommand;
import commands.NoFieldCommandException;
import commands.executes.KillExecute;
import commands.queries.JumpQuery;
import feedback.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Vacuum extends Agent {
    public Map<Buff, Integer> cleaning;
    private static int instanceCount = 0;
    private int vacuumId;

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

    public Vacuum() {
        cleaning = new HashMap<Buff, Integer>();
        ++instanceCount;
        vacuumId = instanceCount;
    }

    @Override
    public void accept(AgentVisitor visitor) {}

    @Override
    public void accept(AgentCommand command) {
        command.visit(this);

        try {
            field.accept(command.getFieldCommand());
        } catch (NoFieldCommandException ignored) {

        }
    }

    @Override
    public void setSpeed(Speed speed) {
        super.setSpeed(new Speed(speed.getDirection(), Math.min(speed.getMagnitude(), 1)));
    }

    @Override
    public boolean onCauseCollision(Agent agent) {
        setSpeed(Speed.getOpposite(getSpeed()));
        JumpQuery bounce = new JumpQuery();
        accept(bounce);
        Logger.log(bounce.getResult());
        return false;
    }

    @Override
    public Agent collide(Agent agent) {
        KillExecute kill = new KillExecute();
        accept(kill);
        Logger.log(kill.getResult());
        return agent;
    }

    public int getVacuumId() {
        return vacuumId;
    }

    @Override
    public String toString() {
        return Character.toString((char)(('a' + vacuumId - 1)));
    }

    private void cleanupRemovedBuffs() {
        ArrayList<Buff> toRemove = new ArrayList<Buff>();
        for (Map.Entry<Buff, Integer> entry : cleaning.entrySet()) {
            if (entry.getKey().getRemoved()) {
                toRemove.add(entry.getKey());
            }
        }
        for (Buff buff : toRemove) {
            cleaning.remove(buff);
        }
    }

    public boolean tryToClean() {
        cleanupRemovedBuffs();
        Buff toClean = field.getFirstCleanableBuff();

        if (toClean == null) {
            System.out.println("vacuumtakarit 1");
            return false;
        }

        Integer state = cleaning.get(toClean);
        if (state == null) {
            cleaning.put(toClean, 1);
            System.out.println("vacuumtakarit 0 1");
        } else {
            state = state - 1;
            if (state == 0) {
                toClean.clean();
            } else {
                cleaning.put(toClean, state);
            }
            System.out.println("vacuumtakarit 0 " + state);
        }
        return true;
    }

}
