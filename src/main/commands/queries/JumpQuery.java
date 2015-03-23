package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.JumpTransmit;
import inspector.Inspector;

public class JumpQuery extends AgentCommand {
    private Speed speed;

    public Speed getSpeed() {
        Inspector.call("ChangeSpeedQuery.getSpeed():Speed");
        Speed tmp = speed.clone();
        Inspector.ret("ChangeSpeedQuery.getSpeed");
        return tmp;
    }

    public void setSpeed(Speed speed) {
        Inspector.call("ChangeSpeedQuery.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("ChangeSpeedQuery.getSpeed");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("JumpQuery.getFieldCommand():FieldCommand");
        JumpTransmit tmp = new JumpTransmit(this);
        Inspector.ret("JumpQuery.getFieldCommand");
        return tmp;
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("JumpQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.call("JumpQuery.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("JumpQuery.visit(Robot)");
        speed = element.getSpeed();
        result.pushMessage(element + " jumping with speed: " + speed);
        Inspector.ret("JumpQuery.visit");
    }
}
