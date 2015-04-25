package commands;

import java.io.Serializable;

import feedback.Feedback;
import feedback.Result;

public abstract class Command implements Feedback, Serializable {

    private static final long serialVersionUID = 6701324582389674206L;

    protected final Result result;
    protected boolean canExecute;

    protected Command() {
        this.result = new Result();
        this.canExecute = true;
    }

    protected Command(Command parent) {
        result = parent.result;
        canExecute = parent.canExecute;
    }

    @Override
    public Result getResult() {
        return result;
    }

    public boolean canExecute() {
        return canExecute;
    }

    public void setExecutable(boolean canExecute) {
        this.canExecute = canExecute;
    }
}
