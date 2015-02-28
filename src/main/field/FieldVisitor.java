package field;

import feedback.Feedback;

public interface FieldVisitor extends Feedback {
    void visit(FieldCell element);
    void visit(EmptyFieldCell element);
    void visit(FinishLineFieldCell element);
}
