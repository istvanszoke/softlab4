package commands.transmits;

import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.ChangeDirectionExecute;
import commands.queries.ChangeDirectionQuery;
import field.Direction;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

/**
 * Irányváltoztatás kérésátviteli osztálya
 * Irányváltotatás kérésnek az átalakítására és átvitelére szolgál
 * Ágensnek küldött parancsból alakul ki, majd a mezők ezt az osztályt
 * felhasználva hozzák létre és adják vissza az Ágenseknek az
 * irányválzotatás kivitelezést
 */
public class ChangeDirectionTransmit extends FieldCommand {
    /**
     * Tervezett irány eltárolása
     */
    private Direction direction;

    /**
     * Osztálykonstruktor
     * Irányváltoztatás kérésből kérésátvitel létrehozása
     *
     * @param parent - A kérés melyet átalakítunk
     */
    public ChangeDirectionTransmit(ChangeDirectionQuery parent) {
        super(parent);
        this.direction = parent.getDirection();
    }

    /**
     * Tervezett irány lekérése
     *
     * @return - A tervezett irány
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Tervezett irány beállítása
     *
     * @param direction - Az új tervezett irány
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Létrehozza az ágens parancsot mely az irányváltoztatás kivitelezés lesz, ha értelmes
     *
     * @return - A létrehozott ágens parancs
     * @throws NoAgentCommandException - Nem értelmes átalakítás esetén
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new ChangeDirectionExecute(this);
    }

    /**
     * Az irányváltoztatás kérésátvitel manipulálásának interfésze
     *
     * @param modifier - A manipuláló osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     *
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FieldCell element) {}

    /**
     * Üres pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     *
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(EmptyFieldCell element) {}

    /**
     * Célvonali pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     *
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element) {}
}
