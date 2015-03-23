package buff;

import agents.*;
import commands.*;
import commands.executes.*;
import commands.queries.*;
import commands.transmits.*;
import feedback.NoFeedbackException;
import feedback.Result;
import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Commandtípusonként és pályaelemenként személyre szabott viselkedéseket biztosító interfész.
 * Az összes releváns Visitornak biztosít egy üres implementációt, illetve közös ősosztály biztosít a játékban
 * található Buffoknak. Ezek segítségével érhető el, hogy a játékos által kiadott parancsok és az Agentek
 * tetszőlegesen módosíthatók legyenek. Interface collection.
 */
public abstract class Buff implements AgentVisitor, AgentCommandVisitor, FieldCommandVisitor {
    /**
     * ChangeDirectionQuery módosítása.
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(ChangeDirectionQuery command) {
        Inspector.call("Buff.visit(ChangeDirectionQuery)");
        Inspector.ret("Buff.visit");
    }

    /**
     * ChangeDirectionExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionExecute command) {
        Inspector.call("Buff.visit(ChangeDirectionExecute)");
        Inspector.ret("Buff.visit");
    }

    /**
     * ChangeSpeedQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedQuery command) {
        Inspector.call("Buff.visit(ChangeSpeedQuery");
        Inspector.ret("Buff.visit");
    }

    /**
     * ChangeSpeedExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedExecute command) {
        Inspector.call("Buff.visit(ChangeSpeedExecute)");
        Inspector.ret("Buff.Buff");
    }

    /**
     * JumpQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpQuery command) {
        Inspector.call("Buff.visit(JumpQuery)");
        Inspector.ret("Buff.visit");
    }

    /**
     * JumpExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpExecute command) {
        Inspector.call("Buff.visit(JumpExecute)");
        Inspector.ret("Buff.visit");
    }

    /**
     * KillExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(KillExecute command) {
        Inspector.call("Buff.visit(KillExecute)");
        Inspector.ret("Buff.visit");
    }

    /**
     * UseStickyQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseStickyQuery command) {
        Inspector.call("Buff.visit(UseStickyQuery)");
        Inspector.ret("Buff.visit");
    }

    /**
     * UseOilQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseOilQuery command) {
        Inspector.call("Buff.visit(UseOilQuery)");
        Inspector.ret("Buff.visit");
    }

    /**
     * Robot módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(Robot element) {
        Inspector.call("Buff.visit(Robot)");
        Inspector.ret("Buff.visit");
    }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionTransmit modifier) {
        Inspector.call("Buff.visit(ChangeDirectionTransmit)");
        Inspector.ret("Buff.visit");
    }

    /**
     * ChangeSpeedTransmit módosítása.
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(ChangeSpeedTransmit modifier) {
        Inspector.call("Buff.visit(ChangeSpeedTransmit)");
        Inspector.ret("Buff.visit");
    }

    /**
     * JumpTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpTransmit modifier) {
        Inspector.call("Buff.visit(JumpTransmit)");
        Inspector.ret("Buff.visit");
    }

    /**
     * UseStickyExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseStickyExecute modifier) {
        Inspector.call("Buff.visit(UseStickyExecute)");
        Inspector.ret("Buff.visit");
    }

    /**
     * UseOilExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseOilExecute modifier) {
        Inspector.call("Buff.visit(UseOilExecute)");
        Inspector.ret("Buff.visit");
    }

    /**
     * Resultot adó metódus.
     * @return - Result
     */
    @Override
    public Result getResult() throws NoFeedbackException {
        Inspector.call("Buff.getResult():Result");
        Inspector.ret("Buff.getResult thrown NoFeedbackException");
        throw new NoFeedbackException();
    }
}
