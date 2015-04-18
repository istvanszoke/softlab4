package commands.queries;

import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.JumpTransmit;

public class JumpQuery extends AgentCommand {
    private Speed speed;

    public Speed getSpeed() {
        return speed.clone();
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
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
        speed = element.getSpeed();
        result.pushDebug(element + " jumping with speed: " + speed);
    }

    @Override
    public void visit(Vacuum element) {

    }
}
