package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import game.Heartbeat;
import game.HeartbeatListener;

/**
 * Olajat reprezentáló osztály.
 * Képes változtatni a ChangeSpeedTransmit és a ChangeDirectionTransmit állapotán
 */
public class Oil extends Buff implements HeartbeatListener {
    long timeRemaining;

    public Oil() {
        timeRemaining = 5000;
        Heartbeat.subscribe(this);
    }

    /**
     * ChangeSpeedTransmit módosítása.
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(ChangeDirectionTransmit command) {
        command.setExecutable(false);
    }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedTransmit command) {
        command.setExecutable(false);
    }

    @Override
    public void onTick(long deltaTime) {
        timeRemaining -= deltaTime;

        if (timeRemaining <= 0) {
            for (BuffListener listener : listeners) {
                listener.onRemove(this);
            }
            Heartbeat.unsubscribe(this);
        }
    }
}
