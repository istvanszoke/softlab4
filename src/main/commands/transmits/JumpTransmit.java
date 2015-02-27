package commands.transmits;

import agents.Speed;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.JumpExecute;
import commands.queries.JumpQuery;
import field.Displacement;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

public class JumpTransmit extends FieldCommand {
    private Displacement displacement;
    private Speed speed;

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

    public JumpTransmit(JumpQuery parent) {
        super(parent.getResult(), parent.canExecute());
        this.speed = parent.getSpeed();
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
        displacement = element.getDisplacement(speed);
    }

    @Override
    public void visit(EmptyFieldCell element) {
        displacement = element.getDisplacement(speed);
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        displacement = element.getDisplacement(speed);
    }
}
