package field;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;

public class EmptyFieldCell extends Field {
    public EmptyFieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    public void onEnter(Agent agent) {
        agent.setField(this);
        this.agent = agent;
        agent.accept(new KillExecute());
    }

    @Override
    protected Field searchGoal(Speed speed) {
        return this;
    }

    @Override
    public void accept(FieldVisitor visitor) {
        removeBuffs();
        visitor.visit(this);
    }

    @Override
    public void accept(FieldCommand command) {
        removeBuffs();
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
