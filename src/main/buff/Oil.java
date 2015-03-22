package buff;

import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import inspector.Inspector;

public class Oil extends Buff {
    @Override
    public void visit(ChangeSpeedTransmit command) {
        Inspector.call("Oil.visit(ChangeSpeedTransmit)");
        command.setExecutable(false);
        Inspector.ret("Oil.visit");
    }

    @Override
    public void visit(ChangeDirectionTransmit command) {
        Inspector.call("Oil.visit(ChangeDirectionTransmit)");
        command.setExecutable(false);
        Inspector.ret("Oil.visit");
    }
}
