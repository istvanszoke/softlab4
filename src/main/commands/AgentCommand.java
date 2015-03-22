package commands;

import agents.AgentVisitor;

/**
 * A játékban ágenseknek küldött parancsok osztálya
 * Feladata, hogy egy ágenst tudjunk utasítani különböző cselekmények végrehajtására
 * A prancsból örököl viszont továbbra is csak egy absztrakt osztályként használt
 */
public abstract class AgentCommand extends Command implements AgentVisitor {
    /**
     * Osztálykonstruktor
     */
    protected AgentCommand() { }

    /**
     * Másoló konstruktor egy ágens parancsra
     * @param parent - A parancs amelyet másolunk
     */
    protected AgentCommand(Command parent) {
        super(parent);
    }

    /**
     * A kapott paracsból egy mezőnek intézett parancsot generáál ahol ez értelmes
     * @return - A mezőnek átadandó parancs
     * @throws NoFieldCommandException - Nem értelmes a mezőparanccsá való átalakítás
     */
    public abstract FieldCommand getFieldCommand() throws NoFieldCommandException;

    /**
     * Az ágens parancsra hatással rendelkező osztályoknak hozzáférés biztosítása
     * @param modifier - A módosító osztály referenciája
     */
    public abstract void accept(AgentCommandVisitor modifier);
}
