package game.control;

import java.util.*;

import commands.AgentCommand;
import field.Field;
import game.handle.VacuumHandle;

public class VacuumController implements GameControllerSocketListener {
    private VacuumHandle vacuum;
    private game.Map map;
    private Field expectedField;

    private Map<AgentCommand, Field> commandQueue;

    public VacuumController(VacuumHandle vacuum, game.Map map) {
        this.vacuum = vacuum;
        this.map = map;
        this.expectedField = null;

        this.commandQueue = new LinkedHashMap<AgentCommand, Field>();
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        if (commandQueue.isEmpty()) {
            calculateCommands();
        }
    }

    @Override
    public void socketClosed(GameControllerSocket sender) {
        if (vacuum.getAgent().getField() != expectedField) {
            commandQueue.clear();
            calculateCommands();
        }
    }

    private void calculateCommands() {
        Field destination = getDestination();

        if (destination == null) {
            return;
        }
    }

    private Field getDestination() {
        List<Field> cleanableFields = new ArrayList<Field>();

        for (Field f : map) {
            if (f.getFirstCleanableBuff() != null) {
                cleanableFields.add(f);
            }
        }

        if (cleanableFields.isEmpty()) {
            return null;
        }

        Field agentField = vacuum.getAgent().getField();
        Field destination = cleanableFields.get(0);
        int minDelta = Math.abs(agentField.getDistanceFromGoal() - destination.getDistanceFromGoal());

        for (Field f : cleanableFields) {
            int newDelta = Math.abs(agentField.getDistanceFromGoal() - f.getDistanceFromGoal());

            if (newDelta < minDelta) {
                destination = f;
                minDelta = newDelta;
            }
        }

        return destination;
    }
}
