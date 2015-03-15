package commands;

import feedback.Feedback;
import feedback.Result;

public abstract class Command implements Feedback {
    protected Result result;
    protected boolean canExecute;

    public Command() {
        this.result = new Result();
        this.canExecute = true;
    }

    public Command(Command parent) {
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
