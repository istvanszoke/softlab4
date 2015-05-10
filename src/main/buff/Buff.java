package buff;

import java.util.ArrayList;
import java.util.List;

import agents.AgentVisitor;
import agents.Robot;
import commands.AgentCommandVisitor;
import commands.FieldCommandVisitor;
import commands.executes.*;
import commands.queries.*;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import commands.transmits.JumpTransmit;
import feedback.NoFeedbackException;
import feedback.Result;

public abstract class Buff implements AgentVisitor, AgentCommandVisitor, FieldCommandVisitor, BuffElement {
    protected List<BuffListener> listeners;
    private boolean isRemoved;
    protected boolean isCleanable;

    public Buff() {
        listeners = new ArrayList<BuffListener>();
        isRemoved = false;
        isCleanable = false;
    }

    public void subscribe(BuffListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(BuffListener listener) {
        listeners.remove(listener);
    }

    protected void remove() {
        if (isRemoved)
            return;
        List<BuffListener> listenerCopies = new ArrayList<BuffListener>(listeners.size());
        for (BuffListener l : listeners) {
            listenerCopies.add(l);
        }

        for (BuffListener listener : listenerCopies) {
            listener.onRemove(this);
        }
        isRemoved = true;
    }

    public boolean getRemoved() {
        return isRemoved;
    }

    public void clean()
    {
        if (getCleanable())
            remove();
    }

    public boolean getCleanable() {
        return !isRemoved && isCleanable;
    }

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
    public void visit(CleanFieldQuery query) {}

    @Override
    public Result getResult() throws NoFeedbackException {
        throw new NoFeedbackException();
    }
}
