package buff;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import agents.Vacuum;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import game.Heartbeat;
import game.HeartbeatListener;

public class Oil extends Buff implements HeartbeatListener, Serializable {

    private static final long serialVersionUID = 1558101339032962867L;

    long timeRemaining;

    public Oil() {
        timeRemaining = 60000;
        Heartbeat.subscribe(this);
        isCleanable = true;
    }

    @Override
    public void visit(ChangeDirectionTransmit command) {
        //command.getResult().pushDebug("Change Direction blocked by Oil");
        command.setExecutable(false);
    }

    @Override
    public void visit(ChangeSpeedTransmit command) {
        //command.getResult().pushDebug("Change Speed blocked by Oil");
        command.setExecutable(false);
    }

    @Override
    public void remove() {
        super.remove();
        Heartbeat.unsubscribe(this);
    }

    @Override
    public void onTick(long deltaTime) {
        timeRemaining -= deltaTime;

        if (timeRemaining <= 0) {
            System.out.println("Olaj felszaradt.");
            remove();
        }
    }

    @Override
    public void visit(Vacuum element) {

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Heartbeat.subscribe(this);
    }

    @Override
    public String toString() {
        return "O";
    }

    @Override
    public void accept(BuffVisitor visitor) {
        visitor.visit(this);
    }

    public double getWear() {
        return timeRemaining / 60000.0;
    }
}
