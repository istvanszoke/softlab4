package commands;

import feedback.Feedback;
import feedback.Result;

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
        this.result = new Result();
        this.canExecute = true;
    }

    /**
     * Gyakorlatilag egy másolókostruktor a Parancs ősosztályhoz
     * @param parent - Az osztály amit másolunk
     */
    protected Command(Command parent) {
        result = parent.result;
        canExecute = parent.canExecute;
    }

    /**
     * Lefuttatott paracs eredményének leérdezése
     * @return - A futtatás eredménye
     */
    @Override
    public Result getResult() {
        return result;
    }

    /**
     * Futtathatóság lekérdezése
     * @return - Futtathatóságra válasz
     */
    public boolean canExecute() {
        return canExecute;
    }

    /**
     * Futtathatóság beállítása
     * @param canExecute - A futtathatóság új értéke
     */
    public void setExecutable(boolean canExecute) {
        this.canExecute = canExecute;
    }
}
