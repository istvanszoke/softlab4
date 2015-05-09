package commands.queries;

import agents.Agent;
import agents.Robot;
import agents.Vacuum;
import commands.AgentCommand;
import commands.AgentCommandVisitor;
import commands.FieldCommand;
import commands.NoFieldCommandException;

public class IdentificationQuery extends AgentCommand {
    private Robot robotAgent;
    private Vacuum vacuumAgent;

    public IdentificationQuery() {
        robotAgent = null;
        vacuumAgent = null;
    }

    @Override
    public FieldCommand getFieldCommand() throws NoFieldCommandException {
        return null;
    }

    @Override
    public void accept(AgentCommandVisitor modifier) {

    }

    @Override
    public void visit(Robot element) {
        robotAgent = element;
    }

    @Override
    public void visit(Vacuum element) {
        vacuumAgent = element;
    }

    public Agent getIdentifiedAgent() {
        if (robotAgent != null) {
            return robotAgent;
        } else if (vacuumAgent != null) {
            return vacuumAgent;
        } else {
            return null;
        }
    }

    public Robot getIdentifiedRobot() {
        return robotAgent;
    }

    public Vacuum getIdentifiedVacuum () {
        return vacuumAgent;
    }
}
