package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;
import inspector.Inspector;

/**
 * Irányváltoztatás kivitelezés egy Ágensen
 */
public class ChangeDirectionExecute extends AgentCommand {
    /**
     * Az irányváltoztatás új iránya
     */
    private Direction direction;

    /**
     * Osztálykostruktor
     * @param parent - Irányváltoztatás kérésátvitel melyből képezzük a kivitelezést
     */
    public ChangeDirectionExecute(ChangeDirectionTransmit parent) {
        super(parent);
        Inspector.call("ChangeDirectionExecute.ChangeDirectionExecute(ChangeDirectionTransmit)");
        this.direction = parent.getDirection();
        Inspector.ret("ChangeDirectionExecute.ChangeDirectionExecute");
    }

    /**
     * Irányváltoztatás új irányának lekérése
     * @return - Irányváltoztatás
     */
    public Direction getDirection() {
        Inspector.call("ChangeDirectionExecute.getDirection():Direction");
        Inspector.ret("ChangeDirectionExecute.getDirection");
        return direction;
    }

    /**
     * Irányváltoztatás új irányának megváltozatása
     * @param direction - Az érték amire változtatunk
     */
    public void setDirection(Direction direction) {
        Inspector.call("ChangeDirectionExecute.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionExecute.setDirection");
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeDirectionExecute.getFieldCommand():FieldCommand");
        Inspector.ret("ChangeDirectionExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeDirectionExecute.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionExecute.accept");
    }

    /**
     * Egy Robot maipulálásához interfész
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeDirectionExecute.visit(Robot)");
        if (!canExecute) {
            result.pushMessage("No direction changed for " + element);
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setDirection(direction);
        element.setSpeed(newSpeed);
        result.pushMessage("Changed direction for " + element + ", new direction is: " + direction);
        Inspector.ret("ChangeDirectionExecute.visit");
    }
}
