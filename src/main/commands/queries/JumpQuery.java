package commands.queries;

import agents.*;
import commands.*;
import commands.transmits.JumpTransmit;
import inspector.Inspector;

/**
 * Ugrás kérés osztály
 * Megtestesít egy kérést az Ágens számára, hogy ugorjon.
 */
public class JumpQuery extends AgentCommand {
    /**
     * A sebesség amivel ugrik
     */
    private Speed speed;

    /**
     * Ugrási sebességet lekérdező függvény
     * @return - Az ugrási sebesség
     */
    public Speed getSpeed() {
        Inspector.call("ChangeSpeedQuery.getSpeed():Speed");
        Speed tmp = speed.clone();
        Inspector.ret("ChangeSpeedQuery.getSpeed");
        return tmp;
    }

    /**
     * Új ugrási sebességet beállító függvény
     * @param speed - Az új ugrási sebesség
     */
    public void setSpeed(Speed speed) {
        Inspector.call("ChangeSpeedQuery.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("ChangeSpeedQuery.getSpeed");
    }

    /**
     * Előállít egy ugrás kérésátvitelt a kérésből
     * @return - Előállított ugrás kérésátvitel
     * @throws NoFieldCommandException - Értelmet esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("JumpQuery.getFieldCommand():FieldCommand");
        JumpTransmit tmp = new JumpTransmit(this);
        Inspector.ret("JumpQuery.getFieldCommand");
        return tmp;
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("JumpQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.call("JumpQuery.accept");
    }

    /**
     * Egy robot maipulálásához szükséges interfész
     * @param element - A mapipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("JumpQuery.visit(Robot)");
        speed = element.getSpeed();
        result.pushMessage(element + " jumping with speed: " + speed);
        Inspector.ret("JumpQuery.visit");
    }
}
