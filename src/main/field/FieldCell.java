package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FieldCell extends Field {

    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
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
