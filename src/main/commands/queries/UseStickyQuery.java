package commands.queries;

import agents.*;
import commands.*;
import commands.executes.UseStickyExecute;

/**
 * Ragacs elhelyezése kérés
 * Megtestesít egy kérést az ágens számára, hogy helyezzen el a készletéből egy ragacsfoltot a pályára
 */
public class UseStickyQuery extends AgentCommand {
    /**
     * Előállít egy Ragacs lehejezés végrehajtást a kérésből
     * @return - Előállított olaj lehejezés végrehajtás
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return new UseStickyExecute(this);
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A hozzáférő osztály osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Egy robot manipulálásához szükséges interfész
     * @param element - A manipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        canExecute = element.useSticky();

        if (canExecute) {
            result.pushMessage(element + " has sticky in its inventory.");
        } else {
            result.pushMessage(element + " has run out of sticky.");
        }
    }
}
