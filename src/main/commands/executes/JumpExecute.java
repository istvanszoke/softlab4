package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.JumpTransmit;
import field.Displacement;

public class JumpExecute extends AgentCommand {
    private Displacement displacement;

    public JumpExecute(JumpTransmit parent) {
        super(parent.getResult(), parent.canExecute());
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
            result.pushMessage("Jumping " + element + " " +
                               "from " + displacement.getStart() +
                               " to " + displacement.getGoal());
        } else {
            result.pushMessage("Cannot execute jump for " + element);
        }
    }
}
