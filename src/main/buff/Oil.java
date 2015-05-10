package buff;

import agents.Vacuum;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import game.Heartbeat;
import game.HeartbeatListener;

public class Oil extends Buff implements HeartbeatListener {
    long timeRemaining;

    public Oil() {
        timeRemaining = 60000;
        Heartbeat.subscribe(this);
        isCleanable = true;
    }

    public static Oil createInactive() {
        Oil oil = new Oil();
        Heartbeat.unsubscribe(oil);
        return oil;
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
