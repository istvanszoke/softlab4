package commands.transmits;

import agents.Speed;
import commands.*;
import commands.executes.JumpExecute;
import commands.queries.JumpQuery;
import field.*;
import inspector.Inspector;

public class JumpTransmit extends FieldCommand {
    private Displacement displacement;
    private Speed speed;

    public JumpTransmit(JumpQuery parent) {
        super(parent);
        Inspector.call("JumpTransmit.JumpTransmit(JumpQuery)");
        this.speed = parent.getSpeed();
        Inspector.ret("JumpTransmit.JumpTransmit");
    }

    public Displacement getDisplacement() {
        Inspector.call("JumpTransmit.getDisplacement():Displacement");
        Inspector.ret("JumpTransmit.getDisplacement()");
        return displacement;
    }

    public void setDisplacement(Displacement displacement) {
        Inspector.call("JumpTransmit.setDisplacement(Displacement)");
        this.displacement = displacement;
        Inspector.ret("JumpTransmit.setDisplacement");
    }

    public Speed getSpeed() {
        Inspector.call("JumpTransmit.getSpeed():Speed");
        Inspector.ret("JumpTransmit.getSpeed");
        return speed;
    }

    public void setSpeed(Speed speed) {
        Inspector.call("JumpTransmit.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("JumpTransmit.setSpeed");
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("JumpTransmit.getAgentCommand():AgentCommand");
        JumpExecute tmp = new JumpExecute(this);
        Inspector.ret("JumpTransmit.getAgentCommand");
        return tmp;
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("JumpTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("JumpTransmit.accept");
    }

    @Override
    public void visit(FieldCell element) {
        Inspector.call("JumpTransmit.visit(FieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }

    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("JumpTransmit.visit(EmptyFieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        Inspector.call("JumpTransmit.visit(FinishLineFieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }
}
