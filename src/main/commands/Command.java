package commands;

import feedback.Feedback;
import feedback.Result;

public abstract class Command implements Feedback {
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
