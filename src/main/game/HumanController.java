package game;

import agents.Agent;
import commands.AgentCommand;
import commands.executes.KillExecute;
import commands.queries.*;
import field.Direction;

import java.awt.event.KeyEvent;

public class HumanController extends AgentController {
    public HumanController(Agent agent) {
        super(agent);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!this.isActive || agent.isOutOfTime() || agent.isDead()) {
            return;
        }

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
                isActive = false;
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
                isActive = false;
                break;
        }
    }

    private void useCommand(AgentCommand command) {
        agent.accept(command);
        System.out.println(command.getResult());
    }
}
