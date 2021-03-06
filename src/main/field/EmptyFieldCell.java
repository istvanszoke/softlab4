package field;

import agents.Agent;
import agents.Speed;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;

public class EmptyFieldCell extends Field {
    public EmptyFieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    @Override
    public boolean onEnter(Agent agent) {
        agent.setField(this);
        this.agent = agent;
        agent.accept(new KillExecute());
        return true;
    }

    @Override
    protected SearchResult searchGoal(Speed speed, int depth) {
        return new SearchResult(this, false);
    }

    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(FieldCommand command) {
        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
