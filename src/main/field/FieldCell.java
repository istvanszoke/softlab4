package field;

import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FieldCell extends Field {
    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(FieldCommand command) {
        acceptBuffs(command);
        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
