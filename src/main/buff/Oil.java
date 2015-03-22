package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;

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
        command.setExecutable(false);
    }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionTransmit command) {
        command.setExecutable(false);
    }
}
