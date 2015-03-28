package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import commands.AgentCommand;
import commands.executes.*;
import commands.queries.*;
import field.Direction;
import game.control.GameControllerSocketListener;
import game.control.GameControllerSocket;

public class HumanController extends KeyAdapter implements GameControllerSocketListener {
    private GameControllerSocket socket;
    private boolean enabled;

    public HumanController(GameControllerSocket socket) {
        this.socket = socket;
        socket.enableStateNotification(this);
        enabled = socket.isOpen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (enabled) {
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
                    useCommandAndChangeAgent(new JumpQuery());
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
                    useCommandAndChangeAgent(new KillExecute());
                    break;
            }
        }
    }

    private void useCommand(AgentCommand command) {
        socket.sendAgentCommand(command);
        System.out.println(command.getResult());
    }

    private void useCommandAndChangeAgent(AgentCommand command) {
        socket.sendAgentCommand(command);
        socket.sendEndTurn();
        System.out.println(command.getResult());
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        enabled = true;
    }

    @Override
    public void socketClosed(GameControllerSocket sender) {
        enabled = false;
    }
}
