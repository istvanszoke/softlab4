package commands.queries;

import agents.*;
import commands.*;
import commands.executes.UseOilExecute;
import inspector.Inspector;

/**
 * Olaj elhelyezése kérés
 * Megtestesít egy kérést az ágens számára, hogy helyezzen el a készletéből egy olajfoltot a pályára
 */
public class UseOilQuery extends AgentCommand {
    /**
     * Előállít egy Olaj lehejezés végrehajtást a kérésből
     * @return - Előállított olaj lehejezés végrehajtás
     * @throws NoFieldCommandException - Értelmetlen esetnél
     */
    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        Inspector.call("UseOilQuery.getFieldCommand():FieldCommand");
        UseOilExecute tmp = new UseOilExecute(this);
        Inspector.ret("UseOilQuery.getFieldCommand");
        return tmp;
    }

    /**
     * Jelen kéréshez hozzáférési interfész
     * @param modifier - A hozzáférő osztály osztály referenciája
     */
    @Override
    public void accept(AgentCommandVisitor modifier) {
        Inspector.call("UseOilQuery.accept(AgentCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("UseOilQuery.accept");
    }

    /**
     * Egy robot manipulálásához szükséges interfész
     * @param element - A manipulálandó robot
     */
    @Override
    public void visit(Robot element) {
        Inspector.call("UseOilQuery.visit(Robot)");
        canExecute = element.useOil();

        if (canExecute) {
            result.pushMessage(element + " has oil in its inventory.");
        } else {
            result.pushMessage(element + " has run out of oil.");
        }
        Inspector.ret("UseOilQuery.visit");
    }
}
