package agents;

import buff.Buff;
import buff.Inventory;
import buff.Oil;
import buff.Sticky;
import commands.AgentCommand;
import commands.NoFieldCommandException;
import commands.executes.KillExecute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vacuum extends Agent {

    public Map<Buff, Integer> cleaning;

    public Vacuum() {
        cleaning = new HashMap<Buff, Integer>();
    }

    @Override
    public void accept(AgentVisitor visitor) {

    }

    @Override
    public void accept(AgentCommand command) {
        command.visit(this);

        try {
            field.accept(command.getFieldCommand());
        } catch (NoFieldCommandException ignored) {

        }
    }

    @Override
    public boolean onCauseCollision(Agent agent) {
        return false;
    }

    @Override
    public Agent collide(Agent agent) {return null;}

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

        if (toClean == null)
            return false;

        Integer state = cleaning.get(toClean);
        if (state == null) {
            cleaning.put(toClean, 1);
        } else {
            state = state - 1;
            if (state == 0) {
                toClean.clean();
            } else {
                cleaning.put(toClean, state);
            }
        }
        return true;
    }

}
