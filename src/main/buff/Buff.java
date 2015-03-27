package buff;

import agents.*;
import commands.*;
import commands.executes.*;
import commands.queries.*;
import commands.transmits.*;
import feedback.NoFeedbackException;
import feedback.Result;

public abstract class Buff implements AgentVisitor, AgentCommandVisitor, FieldCommandVisitor {
    @Override
    public void visit(ChangeDirectionQuery command) { }

    @Override
    public void visit(ChangeDirectionExecute command) { }

    @Override
    public void visit(ChangeSpeedQuery command) { }

    @Override
    public void visit(ChangeSpeedExecute command) { }

    @Override
    public void visit(JumpQuery command) { }

    @Override
    public void visit(JumpExecute command) { }

    @Override
    public void visit(KillExecute command) { }

    @Override
    public void visit(UseStickyQuery command) { }

    @Override
    public void visit(UseOilQuery command) { }

    @Override
    public void visit(Robot element) { }

    @Override
    public void visit(ChangeDirectionTransmit modifier) { }

    @Override
    public void visit(ChangeSpeedTransmit modifier) { }

    @Override
    public void visit(JumpTransmit modifier) { }

    @Override
    public void visit(UseStickyExecute modifier) { }

    @Override
    public void visit(UseOilExecute modifier) { }

    @Override
    public Result getResult() throws NoFeedbackException {
        throw new NoFeedbackException();
    }
}
