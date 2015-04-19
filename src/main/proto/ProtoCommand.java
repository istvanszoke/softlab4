package proto;

import java.util.Map;

public final class ProtoCommand {
    public static final String PLAY = "jatek";
    public static final String TEST = "teszt";
    public static final String JUMP = "ugrik";
    public static final String CHANGE_DIR = "irvalt";
    public static final String CHANGE_SPEED = "sebvalt";
    public static final String USE_OIL = "olajlerak";
    public static final String USE_STICKY = "ragacslerak";
    public static final String PLACE_VACUUM = "vacuumlerak";
    public static final String PLACE_OIL = "olajhelyez";
    public static final String PLACE_STICKY = "ragacshelyez";
    public static final String VACUUM_CLEAN = "vacuumtakarit";
    public static final String STEP_HEARTBEAT = "oraleptet";
    public static final String EXIT = "kilep";
    private final String command;
    private final Map<String, String> args;

    public ProtoCommand() {
        command = "";
        args = null;
    }

    public ProtoCommand(String command, Map<String, String> args) {
        this.command = command;
        this.args = args;
    }

    public final String getCommand() {
        return command;
    }

    public final Map<String, String> getArgs() {
        return args;
    }
}
