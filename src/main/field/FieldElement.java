package field;

import commands.FieldCommand;

public interface FieldElement {
    void accept(FieldVisitor visitor);
    void accept(FieldCommand command);
}
