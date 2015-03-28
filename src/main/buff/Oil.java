package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import game.Heartbeat;
import game.HeartbeatListener;

public class Oil extends Buff implements HeartbeatListener {
    long timeRemaining;

    public Oil() {
        timeRemaining = 5000;
        Heartbeat.subscribe(this);
    }

    @Override
    public void visit(ChangeSpeedTransmit command) {
        command.setExecutable(false);
    }

    @Override
    public void visit(ChangeDirectionTransmit command) {
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
