package game.control;

import commands.AgentCommand;
import commands.executes.KillExecute;
import commands.queries.*;
import field.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HumanController extends KeyAdapter implements GameControllerSocketListener {
    private final ArrayList<GameControllerSocket> sockets;
    private GameControllerSocket currentSocket;

    public HumanController() {
        currentSocket = null;
        sockets = new ArrayList<GameControllerSocket>();
    }

    public void addControllerSocket(GameControllerSocket socket) {
        synchronized (sockets) {
            sockets.add(socket);
        }
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

    private boolean useCommand(AgentCommand command) {
        if (currentSocket == null) {
            currentSocket = searchForActiveSocket();
        }

        if (currentSocket == null) {
            return false;
        }

        if (!currentSocket.sendAgentCommand(command)) {
            currentSocket = searchForActiveSocket();
            if (!(currentSocket == null)) {
                if (!currentSocket.sendAgentCommand(command)) {
                    return false;
                }
            }
        }
        System.out.println(command.getResult());
        return true;
    }

    private void useCommandAndChangeAgent(AgentCommand command) {
        if (useCommand(command)) {
            if (currentSocket != null) {
                currentSocket.sendEndTurn();
            }
        }
    }

    private GameControllerSocket searchForActiveSocket() {
        for (GameControllerSocket socket : sockets) {
            if (socket.isOpen())
                return socket;
        }
        return null;
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        currentSocket = sender;
    }

    @Override
    public void socketClosed(GameControllerSocket sender) { currentSocket = null; }
}
