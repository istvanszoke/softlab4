package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;
import inspector.Inspector;

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
    public ChangeDirectionQuery(Direction direction)
    {
        Inspector.call("ChangeDirectionQuery.ChangeDirectionQuery(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionQuery.ChangeDirectionQuery");
    }

    /**
     * Az kért irányt lekérő függvény
     * @return - A kért irány
     */
    public Direction getDirection()
    {
        Inspector.call("ChangeDirectionQuery.getDirection():Direction");
        Inspector.ret("ChangeDirectionQuery.getDirection");
        return direction;
    }

    /**
     * A kért irányt módosító függvény
     * @param direction - Az új irány
     */
    public void setDirection(Direction direction)
    {
        Inspector.call("ChangeDirectionQuery.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionQuery.setDirection(Direction)");
    }

    /**
     * Előállít egy irányváltoztatás kérésátvitelt a kérésből
     * @return - Az irányváltoztatási kérésátvitel
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeDirectionQuery.getFieldCommand():FieldCommand");
        ChangeDirectionTransmit tmp = new ChangeDirectionTransmit(this);
        Inspector.ret("ChangeDirectionQuery.getFieldCommand");
        return tmp;
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A hozzáférő osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier)
    {
        Inspector.call("ChangeDirectionQuery.accept(AgendCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionQuery.accept");
    }

    /**
     * Egy robot maipulálásához szükséges interfész
     * @param element - A mapipulálandó robot
     */
    @Override
    public void visit(Robot element)
    {
        Inspector.call("ChangeDirectionQuery.accept(Robot)");
        Inspector.ret("ChangeDirectionQuery.accept");
    }
}
