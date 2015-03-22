package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;
import inspector.Inspector;

public class FieldCell extends Field {

    public FieldCell(int distanceFromGoal) {
        super(distanceFromGoal);
    }

    @Override
    public void accept(FieldVisitor visitor) {
        Inspector.call("FieldCell.accept(FieldVisitor)");
        visitor.visit(this);
        Inspector.ret("FieldCell.accept");
    }

    @Override
    public void accept(FieldCommand command) {
        Inspector.call("FieldCell.accept(FieldCommand)");
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
        Inspector.ret("FieldCell.accept");
    }
}
