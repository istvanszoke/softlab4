package agents;

import commands.AgentCommand;

public interface AgentElement {
    void accept(AgentVisitor visitor);

    void accept(AgentCommand command);
}
