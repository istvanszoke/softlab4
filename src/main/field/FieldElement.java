package field;

import commands.FieldCommand;

/* Ez az interf�sz el��rja, hogy az �t implement�l� oszt�lyoknak k�pesnek kell lennie FieldVisitorok �s
 * FieldCommandek fogad�s�ra.
 */
public interface FieldElement {
    /*
     * FieldVisitor fogad�sa.
     * @param visitor - visitor
     */
    void accept(FieldVisitor visitor);

    /*
     * FieldCommand fogad�sa.
     * @param command - visitor
     */
    void accept(FieldCommand command);
}
