package game.control;

import java.util.ArrayList;
import java.util.List;

import commands.AgentCommand;
import commands.executes.KillExecute;
import commands.queries.*;
import feedback.Logger;
import field.Direction;
import proto.ProtoCommand;

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

    public boolean procesProtoCommand(ProtoCommand command) {
		String cmd = command.getCommand();
		if (cmd.equals(ProtoCommand.JUMP)) {
			useCommandAndChangeAgent(new JumpQuery());
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
					return false;
				}
			} else {
				return false;
			}
		} else if (cmd.equals(ProtoCommand.CHANGE_SPEED)) {
			String deltaArg = command.getArgs().get("delta");
			if (deltaArg != null) {
				int delta = Integer.parseInt(deltaArg);
				useCommand(new ChangeSpeedQuery(delta));
			} else {
				return false;
			}
		} else if (cmd.equals(ProtoCommand.USE_OIL)) {
			useCommand(new UseOilQuery());
		} else if (cmd.equals(ProtoCommand.USE_STICKY)) {
			useCommand(new UseStickyQuery());
		} else if (cmd.equals(ProtoCommand.VACUUM_CLEAN)) {
			useCommandAndChangeAgent(new CleanFieldQuery());
		} else {
			return false;
		}
		return true;
    }

    private boolean useCommand(AgentCommand command) {
        GameControllerSocket currentSocket = searchForActiveSocket();

        if (currentSocket == null) {
            return false;
        }

        if (sendCommandTo(command, currentSocket)) {
            Logger.log(command.getResult());
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
            Logger.log(command.getResult());
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
