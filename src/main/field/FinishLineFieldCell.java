package field;

import agents.Speed;
import commands.FieldCommand;
import commands.NoAgentCommandException;

public class FinishLineFieldCell extends Field {

    private static final long serialVersionUID = -5256639349778399249L;

    private Direction startingDirection;

    public FinishLineFieldCell(Direction startingDir) {
        super(0);
        startingDirection = startingDir;
    }

    @Override
    public void accept(FieldVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected SearchResult searchGoal(Speed speed, int depth) {
        Direction speedDir = speed.getDirection();

        if (speedDir != startingDirection) {
            return new SearchResult(this, false);
        }

        return unconditionalSearchGoal(speed, depth);
    }

    @Override
    protected SearchResult unconditionalSearchGoal(Speed speed, int depth) {
        SearchResult result = super.searchGoal(speed, depth);

        if (depth != 0) {
            result.passedFinishLine = true;
        }
        return result;
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
