package buff;

import agents.*;
import commands.*;
import commands.executes.*;
import commands.queries.*;
import commands.transmits.*;
import feedback.NoFeedbackException;
import feedback.Result;

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
    public void visit(ChangeDirectionQuery command) { }

    /**
     * ChangeDirectionExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionExecute command) { }

    /**
     * ChangeSpeedQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedQuery command) { }

    /**
     * ChangeSpeedExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedExecute command) { }

    /**
     * JumpQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpQuery command) { }

    /**
     * JumpExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpExecute command) { }

    /**
     * KillExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(KillExecute command) { }

    /**
     * UseStickyQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseStickyQuery command) { }

    /**
     * UseOilQuery módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseOilQuery command) { }

    /**
     * Robot módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(Robot element) { }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionTransmit modifier) { }
    
    /**
     * ChangeSpeedTransmit módosítása.
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(ChangeSpeedTransmit modifier) { }

    /**
     * JumpTransmit módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(JumpTransmit modifier) { }

    /**
     * UseStickyExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseStickyExecute modifier) { }

    /**
     * UseOilExecute módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(UseOilExecute modifier) { }

    /**
     * Resultot adó metódus.
     * @return - Result
     */
    @Override
    public Result getResult() throws NoFeedbackException {
        throw new NoFeedbackException();
    }
}
