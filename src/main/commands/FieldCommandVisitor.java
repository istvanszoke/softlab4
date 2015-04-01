package commands;

import commands.executes.UseOilExecute;
import commands.executes.UseStickyExecute;
import commands.transmits.ChangeDirectionTransmit;
import commands.transmits.ChangeSpeedTransmit;
import commands.transmits.JumpTransmit;

/**
 * A mező parancsokat módosító osztályoknak interfész
 */
public interface FieldCommandVisitor {
    /**
     * Irányváltoztatási kérésátvitelének módosítója
     * @param modifier - Manipulálandó kérésátvitel
     */
    void visit(ChangeDirectionTransmit modifier);

    /**
     * Sebességváltoztatási kérésátvitelnek módosítója
     * @param modifier - Manipulálandó kérésátvitel
     */
    void visit(ChangeSpeedTransmit modifier);

    /**
     * Utrási kérésátvitel módosítója
     * @param modifier - Manipulálandó kérésátvitel
     */
    void visit(JumpTransmit modifier);

    /**
     * Ragacsfelhasználási kivitelezés módosítója
     * @param modifier - Manipulálandó kivitelezés
     */
    void visit(UseStickyExecute modifier);

    /**
     * Olajfelhasználási kivitelezés módosítója
     * @param modifier - Módosítandó kivitelezés
     */
    void visit(UseOilExecute modifier);
}
