package commands.executes;

import agents.Robot;
import agents.Speed;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.ChangeSpeedTransmit;

/**
 * Sebességváltoztatás kivitelezés egy Ágensen
 */
public class ChangeSpeedExecute extends AgentCommand {
    /**
     * Sebességváltoztatás nagysága
     */
    private int magnitudeDelta;

    /**
     * Osztálykostruktor
     *
     * @param parent - Sebességváltoztatás kérésátvitel melyből képezzük a kivitelezést
     */
    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent);
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    /**
     * Sebességváltoztatás nagyságának lekérése
     *
     * @return - Sebességváltoztatás nagysága
     */
    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    /**
     * Sebességváltoztatás nagyságának megváltoztatása
     *
     * @param magnitudeDelta - Sebességváltoztatás új nagysága
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     *
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     *
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy Robot maipulálásához interfész
     *
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        if (canExecute) {
            Speed newSpeed = element.getSpeed();
            newSpeed.setMagnitude(newSpeed.getMagnitude() + magnitudeDelta);
            element.setSpeed(newSpeed);
            result.pushMessage("Changed speed for " + element + ", new speed is: " + newSpeed.getMagnitude());
        } else {
            result.pushMessage("Failed to change speed for " + element);
        }
    }
}
