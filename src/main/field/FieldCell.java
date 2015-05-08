package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FieldCell extends Field {

    private static final long serialVersionUID = -4169827249003445650L;

    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    @Override
    public void accept(FieldVisitor visitor) {
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
