package agents;

import commands.AgentCommand;
import commands.queries.JumpQuery;

public class Vacuum extends Agent {


    public Vacuum() {}

    @Override
    public void accept(AgentVisitor visitor) {}

    @Override
    public void accept(AgentCommand command) {}

    @Override
    public void setSpeed(Speed speed) {
        super.setSpeed(new Speed(speed.getDirection(), Math.min(speed.getMagnitude(), 1)));
    }

    @Override
    public boolean onCauseCollision(Agent agent) {
        setSpeed(Speed.getOpposite(getSpeed()));
        accept(new JumpQuery());
        return false;
    }

    @Override
    public Agent collide(Agent agent) {
        return agent;
    }
}
