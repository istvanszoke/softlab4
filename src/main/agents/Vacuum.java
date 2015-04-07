package agents;

import buff.Buff;
import buff.Inventory;
import buff.Oil;
import buff.Sticky;
import commands.AgentCommand;
import commands.NoFieldCommandException;
import commands.executes.KillExecute;

import java.util.ArrayList;
import java.util.List;

public class Vacuum extends Agent {


    public Vacuum() {

    }

    @Override
    public void accept(AgentVisitor visitor) {

    }

    @Override
    public void accept(AgentCommand command) {


    }

    @Override
    public boolean onCauseCollision(Agent agent) {
        return false;
    }

    @Override
    public Agent collide(Agent agent) {return null;}

}
