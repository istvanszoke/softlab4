package commands;

import field.FieldVisitor;
import inspector.Inspector;

/**
 * A játékban mezőnek küldött parancsok osztálya
 * Feladata, hogy egy mezőt tudjunk utasítani különböző cselekmények végrehajtására
 * A prancsból örököl viszont továbbra is csak egy absztrakt osztályként használt
 */
public abstract class FieldCommand extends Command implements FieldVisitor {
    /**
     * Osztálykonstruktor
     */
    protected FieldCommand()
    {
        Inspector.call("FieldCommand.FieldCommand()");
        Inspector.ret("FieldCommand.FieldCommand");
    }

    /**
     * Másolókonstruktor a parancshoz
     * @param parent
     */
    protected FieldCommand(Command parent) {
        super(parent);
        Inspector.call("FieldCommand.FieldCommand(Command)");
        Inspector.ret("FieldCommand.FieldCommand");
    }

    /**
     * Ágens paranccsá átalakítás ahol ez értelmes
     * @return - Az átalakított parancs
     * @throws NoAgentCommandException - Nincs értelmes átalakítás
     */
    public abstract AgentCommand getAgentCommand() throws NoAgentCommandException;

    /**
     * A mezőparancsra hatással lévő osztályoknak hozzáférés biztosítása
     * @param modifier - A módosító osztály referenciája
     */
    public abstract void accept(FieldCommandVisitor modifier);
}
