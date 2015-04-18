package game.control;

import java.util.ArrayList;
import java.util.List;

import commands.AgentCommand;
import commands.executes.KillExecute;
import commands.queries.*;
import field.Direction;
import proto.*;

public class ProtoCommandController implements GameControllerSocketListener {
    private final List<GameControllerSocket> sockets;

    public ProtoCommandController() {
        sockets = new ArrayList<GameControllerSocket>();
    }

    public void addControllerSocket(GameControllerSocket socket) {
        synchronized (sockets) {
            sockets.add(socket);
        }
    }

    public void procesProtoCommand(ProtoCommand command) throws InvalidCommandArgumentException, MissingCommandArgumentException {
		String cmd = command.getCommand();
		if (cmd.equals(ProtoCommand.JUMP)) {
			useCommand(new JumpQuery());
		} else if (cmd.equals(ProtoCommand.CHANGE_DIR)) {
			String dirArg = command.getArgs().get("irany");
			if (dirArg != null) {
				if (dirArg.equals("FEL")) {
					useCommand(new ChangeDirectionQuery(Direction.UP));
				} else if (dirArg.equals("LE")) {
					useCommand(new ChangeDirectionQuery(Direction.DOWN));
				} else if (dirArg.equals("BAL")) {
					useCommand(new ChangeDirectionQuery(Direction.LEFT));
				} else if (dirArg.equals("JOBB")) {
					useCommand(new ChangeDirectionQuery(Direction.RIGHT));
				} else {
					throw new InvalidCommandArgumentException();
				}
			} else {
				throw new MissingCommandArgumentException();
			}
		} else if (cmd.equals(ProtoCommand.CHANGE_SPEED)) {

		} else if (cmd.equals(ProtoCommand.USE_OIL)) {

		} else if (cmd.equals(ProtoCommand.USE_STICKY)) {

		}
    }

    private boolean useCommand(AgentCommand command) {
        GameControllerSocket currentSocket = searchForActiveSocket();

        if (currentSocket == null) {
            return false;
        }

        if (sendCommandTo(command, currentSocket)) {
            System.out.println(command.getResult());
            return true;
        }

        return false;
    }

    private void useCommandAndChangeAgent(AgentCommand command) {
        GameControllerSocket currentSocket = searchForActiveSocket();

        if (currentSocket == null) {
            return;
        }

        if (sendCommandTo(command, currentSocket)) {
            System.out.println(command.getResult());
            currentSocket.sendEndTurn();
        }
    }

    private boolean sendCommandTo(AgentCommand command, GameControllerSocket socket) {
        return socket.sendAgentCommand(command);
    }

    private GameControllerSocket searchForActiveSocket() {
        for (GameControllerSocket socket : sockets) {
            if (socket.isOpen()) {
                return socket;
            }
        }
        return null;
    }

    @Override
    public void socketOpened(GameControllerSocket sender) { }

    @Override
    public void socketClosed(GameControllerSocket sender) { }
}
