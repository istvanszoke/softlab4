package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

/**
 * Olajat reprezentáló osztály.
 * Képes változtatni a ChangeSpeedTransmit és a ChangeDirectionTransmit állapotán
 */
public class Oil extends Buff {
    /**
     * ChangeSpeedTransmit módosítása.
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(ChangeSpeedTransmit command) {
        Inspector.call("Oil.visit(ChangeSpeedTransmit)");
        command.setExecutable(false);
        Inspector.ret("Oil.visit");
    }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionTransmit command) {
        Inspector.call("Oil.visit(ChangeDirectionTransmit)");
        command.setExecutable(false);
        Inspector.ret("Oil.visit");
    }
}
