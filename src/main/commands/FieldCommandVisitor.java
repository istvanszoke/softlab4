package commands;

import commands.executes.CleanFieldExecute;
import commands.executes.UseOilExecute;
import commands.executes.UseStickyExecute;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import commands.transmits.JumpTransmit;

public interface FieldCommandVisitor {
    void visit(ChangeDirectionTransmit modifier);

    void visit(ChangeSpeedTransmit modifier);

    void visit(JumpTransmit modifier);

    void visit(UseStickyExecute modifier);

    void visit(UseOilExecute modifier);

    void visit(CleanFieldExecute modifier);
}
