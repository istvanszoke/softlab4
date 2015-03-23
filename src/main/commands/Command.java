package commands;

import feedback.Feedback;
import feedback.Result;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Általános parancs osztály
 * Egymással közvetlen kapcsolatban nem álló objektumok közötti
 * parancsok áramoltatására használt osztályok általános alakja
 */
public abstract class Command implements Feedback {
    /**
     * Eredmény letárolása ha van
     */
    protected final Result result;
    /**
     * Futtathatóság jelzése
     */
    protected boolean canExecute;

    /**
     * Osztálykostruktor
     */
    protected Command() {
        Inspector.call("Command.Command()");
        this.result = new Result();
        this.canExecute = true;
        Inspector.ret("Command.Command");
    }

    /**
     * Gyakorlatilag egy másolókostruktor a Parancs ősosztályhoz
     * @param parent - Az osztály amit másolunk
     */
    protected Command(Command parent) {
        Inspector.call("Command.Command(Command)");
        result = parent.result;
        canExecute = parent.canExecute;
        Inspector.ret("Command.Command");
    }

    /**
     * Lefuttatott paracs eredményének leérdezése
     * @return - A futtatás eredménye
     */
    @Override
    public Result getResult() {
        Inspector.call("Command.getResult():Result");
        Inspector.ret("Command.getResult");
        return result;
    }

    /**
     * Futtathatóság lekérdezése
     * @return - Futtathatóságra válasz
     */
    public boolean canExecute() {
        Inspector.call("Command.canExecute():boolean");
        Inspector.ret("Command.canExecute");
        return canExecute;
    }

    /**
     * Futtathatóság beállítása
     * @param canExecute - A futtathatóság új értéke
     */
    public void setExecutable(boolean canExecute) {
        Inspector.call("Command.setExecutable(boolean)");
        this.canExecute = canExecute;
        Inspector.ret("Command.setExecutable");
    }
}
