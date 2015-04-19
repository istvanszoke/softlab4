package commands.executes;

import agents.Agent;
import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.JumpTransmit;
import field.Displacement;

public class JumpExecute extends AgentCommand {
    private Displacement displacement;

    public JumpExecute(JumpTransmit parent) {
        super(parent);
        this.displacement = parent.getDisplacement();
    }

    public Displacement getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Displacement displacement) {
        this.displacement = displacement;
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
            if (displacement.getGoal().onEnter(element)) {
                displacement.getStart().onExit();
            }
            result.pushNormal("ugrik 0 " + element);
        } else {
            result.pushNormal("ugrik 1 " + element);
        }
    }
}
