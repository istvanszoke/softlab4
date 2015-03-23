package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

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
        Inspector.call("ChangeSpeedQuery.ChangeSpeedQuery(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedQuery.ChangeSpeedQuery");
    }

    /**
     * Kért sebességváltozás lekérése
     * @return - Sebességváltoás
     */
    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedQuery.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedQuery.getMagnitudeDelta");
        return magnitudeDelta;
    }

    /**
     * Kért sebessségváltozás megváltoztatása
     * @param magnitudeDelta - Új sebességváltozás
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedQuery.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedQuery.setMagnitudeDelta");
    }

    /**
     * Előállít egy sebességváltoztatás kérésátvitelt a kérésből
     * @return - Sebességváltotatás kérésátvitel
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeSpeedQuery.getFieldCommand():FieldCommand");
        ChangeSpeedTransmit tmp = new ChangeSpeedTransmit(this);
        Inspector.ret("ChangeSpeedQuery.getFieldCommand");
        return tmp;
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeSpeedQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedQuery.accept");
    }

    /**
     * Egy robot maipulálásához szükséges interfész
     * @param element - A mapipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeSpeedQuery.visit(Robot)");
        Inspector.ret("ChangeSpeedQuery.visit");
    }
}
