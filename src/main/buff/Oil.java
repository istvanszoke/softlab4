package buff;

import agents.Vacuum;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import game.Heartbeat;
import game.HeartbeatListener;

public class Oil extends Buff implements HeartbeatListener {
    long timeRemaining;

    public Oil() {
        timeRemaining = 5000;
        Heartbeat.subscribe(this);
        isCleanable = true;
    }

    @Override
    public void visit(ChangeDirectionTransmit command) {
        command.getResult().pushDebug("Change Direction blocked by Oil");
        command.setExecutable(false);
    }

    @Override
    public void visit(ChangeSpeedTransmit command) {
        command.getResult().pushDebug("Change Speed blocked by Oil");
        command.setExecutable(false);
    }

    @Override
    public void onTick(long deltaTime) {
        timeRemaining -= deltaTime;

        if (timeRemaining <= 0) {
            remove();
            Heartbeat.unsubscribe(this);
            System.out.println("Oil done");
        }
    }

    @Override
    public void visit(Vacuum element) {

    }
}
