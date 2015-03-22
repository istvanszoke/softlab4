package field;

import feedback.Feedback;
/*
 * FieldVisitor.
 * Ezen interf�szt implement�l� oszt�lyok k�pess� v�lnak a Field absztrakt oszt�ly lesz�rmazott oszt�lyainak
 * m�dos�t�s�ra, megv�ltoztat�s�ra, azoknak publikus interf�sz�n kereszt�l.
 */
public interface FieldVisitor extends Feedback {
    /*
     * FieldCell m�dos�t�sa.
     * @param element - Visitelt elem.
     */    
    void visit(FieldCell element);

    /*
     * EmptyFieldCell m�dos�t�sa.
     * @param element - Visitelt elem.
     */    
    void visit(EmptyFieldCell element);

    /*
     * FinishFieldCell m�dos�t�sa.
     * @param element - Visitelt elem.
     */    
    void visit(FinishLineFieldCell element);
}
