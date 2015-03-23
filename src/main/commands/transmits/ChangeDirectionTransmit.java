package commands.transmits;

import commands.*;
import commands.executes.ChangeDirectionExecute;
import commands.queries.ChangeDirectionQuery;
import field.*;
import inspector.Inspector;

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
     * @param parent - A kérés melyet átalakítunk
     */
    public ChangeDirectionTransmit(ChangeDirectionQuery parent) {
        super(parent);
        Inspector.call("ChangeDirectionTransmit:ChangeDirectionTransmit(ChangeDirectionQuery)");
        this.direction = parent.getDirection();
        Inspector.ret("ChangeDirectionTransmit:ChangeDirectionTransmit");
    }

    /**
     * Tervezett irány lekérése
     * @return - A tervezett irány
     */
    public Direction getDirection() {
        Inspector.call("ChangeDirectionTransmit.getDirection():Direction");
        Inspector.ret("ChangeDirectionTransmit.getDirection");
        return direction;
    }

    /**
     * Tervezett irány beállítása
     * @param direction - Az új tervezett irány
     */
    public void setDirection(Direction direction) {
        Inspector.call("ChangeDirectionTransmit.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("ChangeDirectionTransmit.setDirection");
    }

    /**
     * Létrehozza az ágens parancsot mely az irányváltoztatás kivitelezés lesz, ha értelmes
     * @return - A létrehozott ágens parancs
     * @throws NoAgentCommandException - Nem értelmes átalakítás esetén
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("ChangeDirectionTransmit.getAgentCommand():AgentCommand");
        ChangeDirectionExecute tmp = new ChangeDirectionExecute(this);
        Inspector.ret("ChangeDirectionTransmit.getAgentCommand");
        return tmp;
    }

    /**
     * Az irányváltoztatás kérésátvitel manipulálásának interfésze
     * @param modifier - A manipuláló osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("ChangeDirectionTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("ChangeDirectionTransmit.accept");
    }

    /**
     * Pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(FieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }

    /**
     * Üres pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(EmptyFieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(EmptyFieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }

    /**
     * Célvonali pályamező hozzáférési interfésze.
     * Nem végez módosítást a pályamezőn
     * @param element - A hozzáférhető pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element)
    {
        Inspector.call("ChangeDirectionTransmit.visit(FinishLineFieldCell)");
        Inspector.ret("ChangeDirectionTransmit.visit");
    }
}
