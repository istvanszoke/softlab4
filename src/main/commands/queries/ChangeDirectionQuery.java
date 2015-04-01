package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;

/**
 * Irányváltoztatás kérés osztály
 * Megtestesít egy kérést az Ágens számára, hogy váltotassa meg irányát.
 */
public class ChangeDirectionQuery extends AgentCommand {
    /**
     * Az új elvárt irány
     */
    private Direction direction;

    /**
     * Osztálykonstruktor
     * @param direction - Az új kért irány
     */
    public ChangeDirectionQuery(Direction direction) {
        this.direction = direction;
    }

    /**
     * Az kért irányt lekérő függvény
     * @return - A kért irány
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * A kért irányt módosító függvény
     * @param direction - Az új irány
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Előállít egy irányváltoztatás kérésátvitelt a kérésből
     * @return - Az irányváltoztatási kérésátvitel
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new ChangeDirectionTransmit(this);
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A hozzáférő osztály referenciája
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
