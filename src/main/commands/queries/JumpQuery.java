package commands.queries;

import agents.Robot;
import agents.Speed;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.transmits.JumpTransmit;

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
     *
     * @return - Az ugrási sebesség
     */
    public Speed getSpeed() {
        return speed.clone();
    }

    /**
     * Új ugrási sebességet beállító függvény
     *
     * @param speed - Az új ugrási sebesség
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Előállít egy ugrás kérésátvitelt a kérésből
     *
     * @return - Előállított ugrás kérésátvitel
     * @throws NoFieldCommandException - Értelmet esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new JumpTransmit(this);
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     *
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy robot maipulálásához szükséges interfész
     *
     * @param element - A mapipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        speed = element.getSpeed();
        result.pushDebug(element + " jumping with speed: " + speed);
    }
}
