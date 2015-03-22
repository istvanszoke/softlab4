package agents;

import feedback.Feedback;

/**
 * Ágenstípusonként személyre szabott viselkedéseket biztosító interfész.
 * Ezen interfészt implementáló osztályok képessé válnak az Agent absztrakt osztály leszármazott osztályainak
 * megváltoztatására, azoknak publikus interfészén keresztül.
 */
public interface AgentVisitor extends Feedback {
    /**
     * Robot módosítása.
     * @param element - Visitelt elem.
     */    
    void visit(Robot element);
}
