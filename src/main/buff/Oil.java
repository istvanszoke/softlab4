package buff;


import commands.transmits.ChangeSpeedTransmit;
import commands.transmits.ChangeDirectionTransmit;

public class Oil extends Buff {
    @Override
    public void visit(ChangeSpeedTransmit command) {
        command.setExecutable(false);
    }
    @Override
    public void visit(ChangeDirectionTransmit command) {
	command.setExecutable(false);
    }
}
