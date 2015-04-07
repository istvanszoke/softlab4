package field;

import feedback.Feedback;

/**
 * Cellatípusonként személyre szabott viselkedéseket biztosító interfész.
 * Ezen interfészt implementáló osztályok képessé válnak a Field absztrakt osztály leszármazott osztályainak
 * módosítására, megváltoztatására, azoknak publikus interfészén keresztül.
 */
public interface FieldVisitor extends Feedback {
    /**
     * FieldCell módosítása.
     *
     * @param element - Visitelt elem.
     */
    void visit(FieldCell element);

    /**
     * EmptyFieldCell módosítása.
     *
     * @param element - Visitelt elem.
     */
    void visit(EmptyFieldCell element);

    /**
     * FinishFieldCell módosítása.
     *
     * @param element - Visitelt elem.
     */
    void visit(FinishLineFieldCell element);
}
