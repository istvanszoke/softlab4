package commands;

import commands.executes.*;
import commands.transmits.*;

public interface FieldCommandVisitor {
    void visit(ChangeDirectionTransmit modifier);

    void visit(ChangeSpeedTransmit modifier);

    void visit(JumpTransmit modifier);

    void visit(UseStickyExecute modifier);

    void visit(UseOilExecute modifier);
}
