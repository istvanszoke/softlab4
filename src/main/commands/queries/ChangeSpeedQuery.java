package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;

/**
 * Sebességváltoztatás kérés osztály
 * Megtestesít egy kérést az Ágens számára, hogy váltotassa meg sebességét.
 */
public class ChangeSpeedQuery extends AgentCommand {
    /**
     * Tervezett sebességváltoztatás nagyságának deltája
     */
    private int magnitudeDelta;

    /**
     * Osztálykonstruktor
     * Létrehozza a kérést a megfelelő nagyságra
     * @param magnitudeDelta - Az új kért sebességváltoztatás
     */
    public ChangeSpeedQuery(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    /**
     * Kért sebességváltozás lekérése
     * @return - Sebességváltoás
     */
    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    /**
     * Kért sebessségváltozás megváltoztatása
     * @param magnitudeDelta - Új sebességváltozás
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    /**
     * Előállít egy sebességváltoztatás kérésátvitelt a kérésből
     * @return - Sebességváltotatás kérésátvitel
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new ChangeSpeedTransmit(this);
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy robot maipulálásához szükséges interfész
     * @param element - A mapipulálandó robot
     */
    @Override
    public void visit(Robot element) { }
}
