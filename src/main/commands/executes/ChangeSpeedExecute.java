package commands.executes;

import agents.*;
import commands.*;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

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
     * @param parent - Sebességváltoztatás kérésátvitel melyből képezzük a kivitelezést
     */
    public ChangeSpeedExecute(ChangeSpeedTransmit parent) {
        super(parent);
        Inspector.call("ChangeSpeedExecute.ChangeSpeedExecute(ChangeSpeedTransmit)");
        this.magnitudeDelta = parent.getMagnitudeDelta();
        Inspector.ret("ChangeSpeedExecute.ChangeSpeedExecute");
    }

    /**
     * Sebességváltoztatás nagyságának lekérése
     * @return - Sebességváltoztatás nagysága
     */
    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedExecute.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedExecute.getMagnitudeDelta");
        return magnitudeDelta;
    }

    /**
     * Sebességváltoztatás nagyságának megváltoztatása
     * @param magnitudeDelta - Sebességváltoztatás új nagysága
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedExecute.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedExecute.setMagnitudeDelta");
    }

    /**
     * Egy mezőparancsot állít elő a jelen kivitelezésből ha értelmes.
     * Sosem értelmes
     * @return - Nem tér vissza
     * @throws NoFieldCommandException - Mindíg értelmetlen
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("ChangeSpeedExecute.getFieldCommand()");
        Inspector.ret("ChangeSpeedExecute.getFieldCommand thrown NoFieldCommandException");
        throw new NoFieldCommandException();
    }

    /**
     * Jelen kivitelezés módosításához interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("ChangeSpeedExecute.accept(AgentCommandVisitor");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedExecute.accept");
    }

    /**
     * Egy Robot maipulálásához interfész
     * @param element - A manipulálandó robot referenciája
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("ChangeSpeedExecute.visit(Robot)");
        if (canExecute) {
            Speed newSpeed = element.getSpeed();
            newSpeed.setMagnitude(newSpeed.getMagnitude() + magnitudeDelta);
            element.setSpeed(newSpeed);
            result.pushMessage("Changed speed for " + element + ", new speed is: " + newSpeed.getMagnitude());
        } else {
            result.pushMessage("Failed to change speed for " + element);
        }
        Inspector.ret("ChangeSpeedExecute.visit");
    }
}
