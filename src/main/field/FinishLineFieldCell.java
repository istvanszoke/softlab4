package field;

import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FinishLineFieldCell extends Field {

    private static final long serialVersionUID = -5256639349778399249L;

    public FinishLineFieldCell() {
        super(0);
    }

    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(FieldCommand command) {
        acceptBuffs(agent);
        command.visit(this);

        try {
            agent.accept(command.getAgentCommand());
        } catch (NoAgentCommandException ignored) {

        }
    }
}
