package commands.transmits;

import commands.*;
import commands.executes.ChangeSpeedExecute;
import commands.queries.ChangeSpeedQuery;
import field.*;
import inspector.Inspector;

/**
 * Sebességváltoztatás kérésátviteli osztálya
 * Sebességváltotatás kérésnek az átalakítására és átvitelére szolgál
 * Ágensnek küldött parancsból alakul ki, majd a mezők ezt az osztályt
 * felhasználva hozzák létre és adják vissza az Ágenseknek az
 * sebesség válzotatás kivitelezést
 */
public class ChangeSpeedTransmit extends FieldCommand {
    /**
     * Tervezett sebességváltoztatás nagyságának deltája
     */
    private int magnitudeDelta;

    /**
     * Osztálykonstruktor
     * Sebességváltoztatás kérésből kérésátvitel létrehozása
     * @param parent - A kérés melyet átalakítunk
     */
    public ChangeSpeedTransmit(ChangeSpeedQuery parent) {
        super(parent);
        Inspector.call("ChangeSpeedTransmit.ChangeSpeedTransmit(ChangeSpeedQuery)");
        this.magnitudeDelta = parent.getMagnitudeDelta();
        Inspector.ret("ChangeSpeedTransmit.ChangeSpeedTransmit");
    }

    /**
     * Tervezett sebességváltoztatás delta lekérdezése
     * @return - A lekérdezett érték
     */
    public int getMagnitudeDelta() {
        Inspector.call("ChangeSpeedTransmit.getMagnitudeDelta():int");
        Inspector.ret("ChangeSpeedTransmit.getMagnitudeDelta");
        return magnitudeDelta;
    }

    /**
     * Tervezett sebességváltoztatás nagyság delta megváltoztatása
     * @param magnitudeDelta - Új érték
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        Inspector.call("ChangeSpeedTransmit.setMagnitudeDelta(int)");
        this.magnitudeDelta = magnitudeDelta;
        Inspector.ret("ChangeSpeedTransmit.setMagnitudeDelta(int)");
    }

    /**
     * Létrehozza a megfelelő ágens parancsot
     * Mely egy sebességváltotatás kivitelezés
     * @return - A létrehozott ágensparancs
     * @throws NoAgentCommandException - Nem értelmezhető eset
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("ChangeSpeedTransmit.getAgentCommand():AgentCommand");
        ChangeSpeedExecute tmp = new ChangeSpeedExecute(this);
        Inspector.ret("ChangeSpeedTransmit.getAgentCommand");
        return tmp;
    }

    /**
     * Sebességváltoztatás kérésátvitel manipulálásának interfésze
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("ChangeSpeedTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeSpeedTransmit.accept");
    }

    /**
     * Pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(FieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }

    /**
     * Üres pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(EmptyFieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(EmptyFieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }

    /**
     * Célvonali pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element)
    {
        Inspector.call("ChangeSpeedTransmit.visit(FinishLineFieldCell)");
        Inspector.ret("ChangeSpeedTransmit.visit");
    }
}
