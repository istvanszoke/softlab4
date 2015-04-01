package commands.queries;

import agents.Robot;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;
import commands.executes.UseOilExecute;

/**
 * Olaj elhelyezése kérés
 * Megtestesít egy kérést az ágens számára, hogy helyezzen el a készletéből egy olajfoltot a pályára
 */
public class UseOilQuery extends AgentCommand {
    /**
     * Előállít egy Olaj lehejezés végrehajtást a kérésből
     *
     * @return - Előállított olaj lehejezés végrehajtás
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseOilExecute(this);
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     *
     * @param modifier - A hozzáférő osztály osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy robot manipulálásához szükséges interfész
     *
     * @param element - A manipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        canExecute = element.useOil();

        if (canExecute) {
            result.pushMessage(element + " has oil in its inventory.");
        } else {
            result.pushMessage(element + " has run out of oil.");
        }
    }
}
