package commands.transmits;

import agents.Speed;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.JumpExecute;
import commands.queries.JumpQuery;
import field.*;

public class JumpTransmit extends FieldCommand {

    private static final long serialVersionUID = -7576028340378330362L;

    private Displacement displacement;
    private Speed speed;
    private boolean isUnconditional;

    public JumpTransmit(JumpQuery parent) {
        super(parent);
        this.speed = parent.getSpeed();
        this.isUnconditional = parent.isUnconditional();
    }

    public Displacement getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Displacement displacement) {
        this.displacement = displacement;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new JumpExecute(this);
    }

    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    @Override
    public void visit(FieldCell element) {
        visitCommon(element);
    }

    @Override
    public void visit(EmptyFieldCell element) {
        visitCommon(element);
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        visitCommon(element);
    }

    private void visitCommon(Field element) {
        if (isUnconditional) {
            displacement = element.getUnconditionalDisplacement(speed);
        } else {
            displacement = element.getDisplacement(speed);
        }
    }
}
