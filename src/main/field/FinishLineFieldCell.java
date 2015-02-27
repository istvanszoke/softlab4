package field;

import buff.Buff;
import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FinishLineFieldCell extends Field {
    public FinishLineFieldCell() {
        super(0);
    }

    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(FieldCommand command) {
        for (Buff b : buffs) {
            command.accept(b);
        }

        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch(NoAgentCommandException ignored) {

        }
    }
}
