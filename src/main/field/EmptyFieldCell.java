package field;

import agents.Agent;
import agents.Speed;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;

public class EmptyFieldCell extends Field {

    private static final long serialVersionUID = 7436438583584464531L;

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
    protected Field searchGoal(Speed speed) {
        return this;
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
