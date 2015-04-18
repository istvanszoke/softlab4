package proto;

import java.util.Map;

public final class ProtoCommand {
    public static final String PLAY = "jatek";
    public static final String LOAD = "betolt";
    public static final String SAVE = "ment";
    public static final String JUMP = "ugrik";
    public static final String CHANGE_DIR = "irvalt";
    public static final String CHANGE_SPEED = "sebvalt";
    public static final String USE_OIL = "olajlerak";
    public static final String USE_STICKY = "ragacslerak";
    public static final String PLACE_VACUUM = "vacuumlerak";
    public static final String STEP_OIL = "olajleptet";
    public static final String PLACE_OIL = "olajhelyez";
    public static final String PLACE_STICKY = "ragacshelyez";
    public static final String VACUUM_CLEAN = "vacuumtakarit";
    public static final String STEP_VACUUM = "vacuumlep";
    public static final String LIST_FIELD = "listfield";
    public static final String LIST_ROBOT = "listrobot";
    public static final String LIST_VACUUM = "listvacuum";
    public static final String LIST_ALL_AGENTS = "listagents";
    public static final String LIST_ALL_FIELDS = "listfields";
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
