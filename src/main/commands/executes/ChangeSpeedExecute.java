package commands.executes;

import agents.Agent;
import agents.Robot;
import agents.Speed;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeSpeedTransmit;

public class ChangeSpeedExecute extends AgentCommand {

    private static final long serialVersionUID = 282496136538937406L;

    private int magnitudeDelta;

    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent);
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(Robot element) {
        visitCommon(element);
    }

    @Override
    public void visit(Vacuum element) {
        visitCommon(element);
    }

    private void visitCommon(Agent element) {
        if (canExecute) {
            Speed newSpeed = element.getSpeed();
            newSpeed.setMagnitude(newSpeed.getMagnitude() + magnitudeDelta);
            element.setSpeed(newSpeed);
            result.pushNormal("sebvalt 0 " + element + " " + newSpeed.getMagnitude());
        } else {
            result.pushNormal("sebvalt 1 " + element);
        }
    }
}
