package commands.transmits;

import agents.Speed;
import commands.AgentCommand;
import commands.FieldCommand;
import commands.FieldCommandVisitor;
import commands.NoAgentCommandException;
import commands.executes.JumpExecute;
import commands.queries.JumpQuery;
import field.Displacement;
import field.EmptyFieldCell;
import field.FieldCell;
import field.FinishLineFieldCell;

/**
 * Ugrás kérésátviteli osztálya
 * Ugrás kérésnek az átalakítására és átvitelére szolgál
 * Ágensnek küldött parancsból alakul ki, majd a mezők ezt az osztályt
 * felhasználva hozzák létre és adják vissza az Ágenseknek az
 * ugrás kivitelezést
 */
public class JumpTransmit extends FieldCommand {
    /**
     * Elmozdulást tároló változó
     */
    private Displacement displacement;
    /**
     * Sebességet tároló változó
     */
    private Speed speed;

    /**
     * Osztálykonstruktor
     * Ugrás kérésből kérésátvitel létrehozása
     * @param parent - Kérés melyet átalakítunk
     */
    public JumpTransmit(JumpQuery parent) {
        super(parent);
        this.speed = parent.getSpeed();
    }

    /**
     * Tárolt elmozdulást visszaadó függvény
     * @return - A tárolt elmozdulás
     */
    public Displacement getDisplacement() {
        return displacement;
    }

    /**
     * Tárolt elmozdulást módosító függvény
     * @param displacement - Az új elmozdulás
     */
    public void setDisplacement(Displacement displacement) {
        this.displacement = displacement;
    }

    /**
     * Tárolt sebességet visszadó függvény
     * @return - A tárolt sebesség
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * Tárolt sebességet módosító függvény
     * @param speed - Az új sebesség
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Létrehozza a megfelelő ágens parancsot
     * Mely egy ugrás kivitelezés
     * @return - A létrehozott ágensparancs
     * @throws NoAgentCommandException - Nem értelmezhető eset
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        return new JumpExecute(this);
    }

    /**
     * Ugrás kérésátvitel manipulálásának interfésze
     * @param modifier - A manipuláló osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        modifier.visit(this);
    }

    /**
     * Pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(FieldCell element) {
        displacement = element.getDisplacement(speed);
    }

    /**
     * Üres pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(EmptyFieldCell element) {
        displacement = element.getDisplacement(speed);
    }

    /**
     * Célvonali pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element) {
        displacement = element.getDisplacement(speed);
    }
}
