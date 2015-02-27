package buff;


import commands.transmits.ChangeSpeedTransmit;

public class Oil extends Buff {
    @Override
    public void visit(ChangeSpeedTransmit command) {
        command.setMagnitudeDelta(0);
    }
}
