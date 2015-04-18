package agents;

import feedback.Feedback;

public interface AgentVisitor extends Feedback {
    void visit(Robot element);
    void visit(Vacuum element);
}
