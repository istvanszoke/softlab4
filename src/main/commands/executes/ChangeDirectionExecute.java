package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeDirectionTransmit;
import field.Direction;

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
        this.direction = parent.getDirection();
    }

    /**
     * Irányváltoztatás új irányának lekérése
     * @return - Irányváltoztatás
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Irányváltoztatás új irányának megváltozatása
     * @param direction - Az érték amire változtatunk
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy Robot maipulálásához interfész
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        if (!canExecute) {
            result.pushMessage("No direction changed for " + element);
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setDirection(direction);
        element.setSpeed(newSpeed);
        result.pushMessage("Changed direction for " + element + ", new direction is: " + direction);
    }
}
