package agents;

import commands.AgentCommand;

/**
 * Parancsok és visitorok fogadására képes interfész.
 * Ez az interfész előírja, hogy az őt implementáló osztályoknak képesnek
 * kell lennie AgentVisitorok és AgentCommandek fogadására.
 */
public interface AgentElement {

    /**
     * AgentVisitor fogadása.
     *
     * @param visitor - visitor
     */
    void accept(AgentVisitor visitor);

    /**
     * AgentCommand fogadása.
     *
     * @param command - command
     */
    void accept(AgentCommand command);
}
