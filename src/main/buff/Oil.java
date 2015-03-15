package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;

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
