package commands.queries;

import agents.Agent;
import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.JumpTransmit;

public class JumpQuery extends AgentCommand {

    private static final long serialVersionUID = 1882648361848672688L;

    private Speed speed;
    private boolean isUnconditional = false;

    public Speed getSpeed() {
        return speed.clone();
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public boolean isUnconditional() {
        return isUnconditional;
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new JumpTransmit(this);
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        visitCommon(element);
        result.pushDebug(element + " jumping with speed: " + speed);
    }

    @Override
    public void visit(Vacuum element) {
        visitCommon(element);
        isUnconditional = true;
    }

    private void visitCommon(Agent element) {
        speed = element.getSpeed();
    }
}
