package commands;

import commands.executes.*;
import commands.queries.*;

/**
 * Ágens parancsot módosítókat kezelő interfész
 */
public interface AgentCommandVisitor {
    /**
     * Iránymódosító kérés manipulálása
     * @param command - Manipulálandó kérés
     */
    void visit(ChangeDirectionQuery command);
    /**
     * Iránymódosítás kivitelezés manipulálása
     * @param command - Manipulálandó kivitelezés
     */
    void visit(ChangeDirectionExecute command);
    /**
     * Sebességmódosító kérés manipulálása
     * @param command - Manipulálandó kérés
     */
    void visit(ChangeSpeedQuery command);
    /**
     * Sebességmódosító kivitelezés manipulálása
     * @param command - Manipulálandó kivitelezés
     */
    void visit(ChangeSpeedExecute command);
    /**
     * Úgrás kérés manipulálása
     * @param command - Manipulálandó kérés
     */
    void visit(JumpQuery command);
    /**
     * Ugrás kivitelezés manipulálása
     * @param command - Manipulálandó kivitelezés
     */
    void visit(JumpExecute command);
    /**
     * Megölés kivittelezés manipulálása
     * @param command - Manipulálandó kivitelezés
     */
    void visit(KillExecute command);
    /**
     * Ragacs használat kérés manipulálása
     * @param command - Manipulálandó kérés
     */
    void visit(UseStickyQuery command);
    /**
     * Olaj használat kérés manipulálása
     * @param command - Manipulálandó kérés
     */
    void visit(UseOilQuery command);
}
