package commands;

import feedback.Feedback;
import feedback.Result;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

public abstract class Command implements Feedback {
    protected final Result result;
    protected boolean canExecute;

    protected Command() {
        Inspector.call("Command.Command()");
        this.result = new Result();
        this.canExecute = true;
        Inspector.ret("Command.Command");
    }

    protected Command(Command parent) {
        Inspector.call("Command.Command(Command)");
        result = parent.result;
        canExecute = parent.canExecute;
        Inspector.ret("Command.Command");
    }

    @Override
    public Result getResult() {
        Inspector.call("Command.getResult():Result");
        Inspector.ret("Command.getResult");
        return result;
    }

    public boolean canExecute() {
        Inspector.call("Command.canExecute():boolean");
        Inspector.ret("Command.canExecute");
        return canExecute;
    }

    public void setExecutable(boolean canExecute) {
        Inspector.call("Command.setExecutable(boolean)");
        this.canExecute = canExecute;
        Inspector.ret("Command.setExecutable");
    }
}
