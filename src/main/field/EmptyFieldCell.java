package field;

import agents.Agent;
import agents.Speed;
import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import commands.executes.KillExecute;
import inspector.Inspector;

public class EmptyFieldCell extends Field {
    public EmptyFieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    public void onEnter(Agent agent) {
        Inspector.call("EmptyFieldCell.onEnter(Agent)");
        agent.setField(this);
        this.agent = agent;
        agent.accept(new KillExecute());
        Inspector.ret("EmptyFieldCell.onEnter");
    }

    @Override
    protected Field searchGoal(Speed speed) {
        Inspector.call("EmptyFieldCell.searchGoal(Speed):Field");
        Inspector.ret("EmptyFieldCell.searchGoal");
        return this;
    }

    @Override
    public void accept(FieldVisitor visitor) {
        Inspector.call("EmptyFieldCell.accept(FieldVisitor)");
        visitor.visit(this);
        Inspector.ret("EmptyFieldCell.accept");
    }

    @Override
    public void accept(FieldCommand command) {
        Inspector.call("EmptyFieldCell.accept(FieldCommand)");
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
        Inspector.ret("EmptyFieldCell.accept");
    }
}
