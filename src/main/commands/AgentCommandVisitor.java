package commands;

import commands.executes.ChangeDirectionExecute;
import commands.executes.ChangeSpeedExecute;
import commands.executes.JumpExecute;
import commands.executes.KillExecute;
import commands.queries.*;

public interface AgentCommandVisitor {
    void visit(ChangeDirectionQuery command);

    void visit(ChangeDirectionExecute command);

    void visit(ChangeSpeedQuery command);

    void visit(ChangeSpeedExecute command);

    void visit(JumpQuery command);

    void visit(JumpExecute command);

    void visit(KillExecute command);

    void visit(UseStickyQuery command);

    void visit(UseOilQuery command);
}
