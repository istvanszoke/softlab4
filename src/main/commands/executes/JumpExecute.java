package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.JumpTransmit;
import field.Displacement;
import inspector.Inspector;

public class JumpExecute extends AgentCommand {
    private Displacement displacement;

    public JumpExecute(JumpTransmit parent) {
        super(parent);
        Inspector.call("JumpExecute.JumpExecute(JumpTransmit)");
        this.displacement = parent.getDisplacement();
        Inspector.ret("JumpExecute.JumpExecute");
    }

    public Displacement getDisplacement() {
        Inspector.call("JumpExecute.getDisplacement():Displacement");
        Inspector.ret("JumpExecute.getDisplacement");
        return displacement;
    }

    public void setDisplacement(Displacement displacement) {
        Inspector.call("JumpExecute.setDisplacement(Displacement)");
        this.displacement = displacement;
        Inspector.ret("JumpExecute.setDispalcement");
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("JumpExecute.getFieldCommand():FieldCommand");
        Inspector.ret("JumpExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("JumpExecute.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("JumpExecute.accept");
    }

    @Override
    public void visit(Robot element) {
        Inspector.call("JumpExecute.visit(Robot)");
        if (canExecute) {
            displacement.getStart().onExit();
            displacement.getGoal().onEnter(element);
            result.pushMessage("Jumping " + element + " " +
                               "from " + displacement.getStart() +
                               " to " + displacement.getGoal());
        } else {
            result.pushMessage("Cannot execute jump for " + element);
        }
        Inspector.ret("JumpExecute.visit");
    }
}
