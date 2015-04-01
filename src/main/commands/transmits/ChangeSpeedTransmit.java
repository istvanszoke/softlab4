package commands.transmits;

import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.ChangeSpeedExecute;
import commands.queries.ChangeSpeedQuery;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

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
        this.magnitudeDelta = parent.getMagnitudeDelta();
    }

    /**
     * Tervezett sebességváltoztatás delta lekérdezése
     * @return - A lekérdezett érték
     */
    public int getMagnitudeDelta() {
        return magnitudeDelta;
    }

    /**
     * Tervezett sebességváltoztatás nagyság delta megváltoztatása
     * @param magnitudeDelta - Új érték
     */
    public void setMagnitudeDelta(int magnitudeDelta) {
        this.magnitudeDelta = magnitudeDelta;
    }

    /**
     * Létrehozza a megfelelő ágens parancsot
     * Mely egy sebességváltotatás kivitelezés
     * @return - A létrehozott ágensparancs
     * @throws NoAgentCommandException - Nem értelmezhető eset
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new ChangeSpeedExecute(this);
    }

    /**
     * Sebességváltoztatás kérésátvitel manipulálásának interfésze
     * @param modifier - A módosító osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FieldCell element) {}

    /**
     * Üres pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(EmptyFieldCell element) {}

    /**
     * Célvonali pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element) {}
}
