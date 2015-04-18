package commands.executes;

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
        if (canExecute) {
            displacement.getStart().onExit();
            displacement.getGoal().onEnter(element);
            result.pushDebug("Jumping " + element + " " +
                             "from " + displacement.getStart() +
                             " to " + displacement.getGoal());
        } else {
            result.pushDebug("Cannot execute jump for " + element);
        }
    }

    @Override
    public void visit(Vacuum element) {

    }
}
