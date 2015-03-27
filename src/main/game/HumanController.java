package game;

import java.awt.event.KeyEvent;

import agents.Agent;
import commands.AgentCommand;
import commands.executes.KillExecute;
import commands.queries.*;
import field.Direction;

public class HumanController extends AgentController {
    public HumanController(Game game) {
        super(game);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Change Direction
            case KeyEvent.VK_W:
                useCommand(new ChangeDirectionQuery(Direction.UP));
                break;
            case KeyEvent.VK_A:
                useCommand(new ChangeDirectionQuery(Direction.LEFT));
                break;
            case KeyEvent.VK_S:
                useCommand(new ChangeDirectionQuery(Direction.DOWN));
                break;
            case KeyEvent.VK_D:
                useCommand(new ChangeDirectionQuery(Direction.RIGHT));
                break;

            // Jump
            case KeyEvent.VK_SPACE:
                useCommand(new JumpQuery());
                game.onAgentChange();
                break;

            // Change Speed
            case KeyEvent.VK_UP:
                useCommand(new ChangeSpeedQuery(1));
                break;
            case KeyEvent.VK_DOWN:
                useCommand(new ChangeSpeedQuery(-1));
                break;

            // Place buffs
            case KeyEvent.VK_R:
                useCommand(new UseOilQuery());
                break;
            case KeyEvent.VK_F:
                useCommand(new UseStickyQuery());
                break;

            // Suicide
            case KeyEvent.VK_K:
                useCommand(new KillExecute());
                game.onAgentChange();
                break;
        }
    }

    private void useCommand(AgentCommand command) {
        Agent agent = game.getCurrentAgent();
        if (agent == null) {
            return;
        }

        agent.accept(command);
        System.out.println(command.getResult());
    }
}
