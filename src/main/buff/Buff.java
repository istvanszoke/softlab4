package buff;

import java.util.ArrayList;
import java.util.List;

import agents.AgentVisitor;
import agents.Robot;
import commands.AgentCommandVisitor;
import commands.FieldCommandVisitor;
import commands.executes.*;
import commands.queries.*;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import commands.transmits.JumpTransmit;
import feedback.NoFeedbackException;
import feedback.Result;

/**
 * Commandtípusonként és pályaelemenként személyre szabott viselkedéseket biztosító interfész.
 * Az összes releváns Visitornak biztosít egy üres implementációt, illetve közös ősosztály biztosít a játékban
 * található Buffoknak. Ezek segítségével érhető el, hogy a játékos által kiadott parancsok és az Agentek
 * tetszőlegesen módosíthatók legyenek. Interface collection.
 */
public abstract class Buff implements AgentVisitor, AgentCommandVisitor, FieldCommandVisitor {
    protected List<BuffListener> listeners;

    public Buff() {
        listeners = new ArrayList<BuffListener>();
    }

    public void subscribe(BuffListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(BuffListener listener) {
        listeners.remove(listener);
    }

    /**
     * ChangeDirectionQuery módosítása.
     * @param command - Visitelt elem.
     */
    @Override
    public void visit(ChangeDirectionQuery command) { }

    /**
     * ChangeDirectionExecute módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionExecute command) { }

    /**
     * ChangeSpeedQuery módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedQuery command) { }

    /**
     * ChangeSpeedExecute módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedExecute command) { }

    /**
     * JumpQuery módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(JumpQuery command) { }

    /**
     * JumpExecute módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(JumpExecute command) { }

    /**
     * KillExecute módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(KillExecute command) { }

    /**
     * UseStickyQuery módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(UseStickyQuery command) { }

    /**
     * UseOilQuery módosítása.
     * @param command - Visitelt elem.
     */
    @Override
    public void visit(UseOilQuery command) { }

    /**
     * Robot módosítása.
     * @param command - Visitelt elem.
     */    
    @Override
    public void visit(Robot command) { }

    /**
     * ChangeDirectionTransmit módosítása.
     * @param modifier - Visitelt elem.
     */    
    @Override
    public void visit(ChangeDirectionTransmit modifier) { }

    /**
     * ChangeSpeedTransmit módosítása.
     * @param modifier - Visitelt elem.
     */    
    @Override
    public void visit(ChangeSpeedTransmit modifier) { }

    @Override
    public void visit(JumpTransmit modifier) { }

    @Override
    public void visit(UseStickyExecute modifier) { }

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
