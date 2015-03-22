package field;

import commands.FieldCommand;

/* Ez az interfész elõírja, hogy az õt implementáló osztályoknak képesnek kell lennie FieldVisitorok és
 * FieldCommandek fogadására.
 */
public interface FieldElement {
    /*
     * FieldVisitor fogadása.
     * @param visitor - visitor
     */
    void accept(FieldVisitor visitor);

    /*
     * FieldCommand fogadása.
     * @param command - visitor
     */
    void accept(FieldCommand command);
}
