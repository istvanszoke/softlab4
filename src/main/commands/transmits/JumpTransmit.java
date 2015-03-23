package commands.transmits;

import agents.Speed;
import commands.*;
import commands.executes.JumpExecute;
import commands.queries.JumpQuery;
import field.*;
import inspector.Inspector;

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
        Inspector.call("JumpTransmit.JumpTransmit(JumpQuery)");
        this.speed = parent.getSpeed();
        Inspector.ret("JumpTransmit.JumpTransmit");
    }

    /**
     * Tárolt elmozdulást visszaadó függvény
     * @return - A tárolt elmozdulás
     */
    public Displacement getDisplacement() {
        Inspector.call("JumpTransmit.getDisplacement():Displacement");
        Inspector.ret("JumpTransmit.getDisplacement()");
        return displacement;
    }

    /**
     * Tárolt elmozdulást módosító függvény
     * @param displacement - Az új elmozdulás
     */
    public void setDisplacement(Displacement displacement) {
        Inspector.call("JumpTransmit.setDisplacement(Displacement)");
        this.displacement = displacement;
        Inspector.ret("JumpTransmit.setDisplacement");
    }

    /**
     * Tárolt sebességet visszadó függvény
     * @return - A tárolt sebesség
     */
    public Speed getSpeed() {
        Inspector.call("JumpTransmit.getSpeed():Speed");
        Inspector.ret("JumpTransmit.getSpeed");
        return speed;
    }

    /**
     * Tárolt sebességet módosító függvény
     * @param speed - Az új sebesség
     */
    public void setSpeed(Speed speed) {
        Inspector.call("JumpTransmit.setSpeed(Speed)");
        this.speed = speed;
        Inspector.ret("JumpTransmit.setSpeed");
    }

    /**
     * Létrehozza a megfelelő ágens parancsot
     * Mely egy ugrás kivitelezés
     * @return - A létrehozott ágensparancs
     * @throws NoAgentCommandException - Nem értelmezhető eset
     */
    @Override
    public AgentCommand getAgentCommand() throws NoAgentCommandException {
        Inspector.call("JumpTransmit.getAgentCommand():AgentCommand");
        JumpExecute tmp = new JumpExecute(this);
        Inspector.ret("JumpTransmit.getAgentCommand");
        return tmp;
    }

    /**
     * Ugrás kérésátvitel manipulálásának interfésze
     * @param modifier - A manipuláló osztály referenciája
     */
    @Override
    public void accept(FieldCommandVisitor modifier) {
        Inspector.call("JumpTransmit.accept(FieldCommandVisitor)");
        modifier.visit(this);
        Inspector.ret("JumpTransmit.accept");
    }

    /**
     * Pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(FieldCell element) {
        Inspector.call("JumpTransmit.visit(FieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }

    /**
     * Üres pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(EmptyFieldCell element) {
        Inspector.call("JumpTransmit.visit(EmptyFieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }

    /**
     * Célvonali pályamező hozzáférési interfésze
     * Nem végez módosítást a pályamezőn
     * @param element -A hozzáférő pályamező
     */
    @Override
    public void visit(FinishLineFieldCell element) {
        Inspector.call("JumpTransmit.visit(FinishLineFieldCell)");
        displacement = element.getDisplacement(speed);
        Inspector.ret("JumpTransmit.visit");
    }
}
