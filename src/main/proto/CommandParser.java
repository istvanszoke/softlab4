package proto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandParser {
    //TODO: Change the argument of betolt() and ment() from nev to allas in the docs.
    private static final Map<String, String[]> acceptedCommands = Collections.unmodifiableMap(new HashMap<String, String[]>() {{
        put(ProtoCommand.PLAY, new String[]{"palya"});
        put(ProtoCommand.JUMP, new String[]{});
        put(ProtoCommand.CHANGE_DIR, new String[]{"irany"});
        put(ProtoCommand.CHANGE_SPEED, new String[]{"delta"});
        put(ProtoCommand.USE_OIL, new String[]{});
        put(ProtoCommand.USE_STICKY, new String[]{});
        put(ProtoCommand.VACUUM_CLEAN, new String[]{});
        put(ProtoCommand.STEP_HEARTBEAT, new String[]{"ido"});
        put(ProtoCommand.EXIT, new String[]{});



    }});

    private static final Map<String, String> argumentValidators = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("szam", "[2-4]");
        put("ido", "[0-9]+");
        put("palya", "[A-Za-z0-9]+\\.map");
        put("irany", "(FEL|LE|BAL|JOBB)");
        put("delta", "[+-]1");
        put("field", "[0-9]+");
    }});

    public static ProtoCommand parse(String commandString) throws InvalidCommandException, InvalidCommandArgumentException {
        String stripped = commandString.replaceAll("\\s", "");

        Pattern syntaxRegex = Pattern.compile("([A-Za-z]+?(?=[(]))" +
                                              "\\(" +
                                              "(([A-Za-z0-9]+=\\S+,?)*)" +
                                              "(?<!,)\\)");
        Matcher syntax = syntaxRegex.matcher(stripped);
        if (!syntax.matches()) {
            throw new InvalidCommandException();
        }

        String command = syntax.group(1);
        if (!acceptedCommands.containsKey(command)) {
            throw new InvalidCommandException();
        }

        Map<String, String> args = parseArgs(command, syntax.group(2));
        return new ProtoCommand(command, args);
    }

    private static Map<String, String> parseArgs(String command, String rawArgs) throws InvalidCommandArgumentException {
        if (rawArgs.equals("") && acceptedCommands.get(command).length == 0) {
            return new HashMap<String, String>();
        }

        String[] splitArguments = rawArgs.split(",");
        String[] validArguments = acceptedCommands.get(command);

        if (splitArguments.length != validArguments.length) {
            throw new InvalidCommandArgumentException();
        }

        Map<String, String> args = new HashMap<String, String>();
        for (String argument : splitArguments) {
            String[] keyValue = argument.split("=");

            if (!contains(validArguments, keyValue[0])) {
                throw new InvalidCommandArgumentException();
            }

            if (!validateArgumentValue(keyValue)) {
                throw new InvalidCommandArgumentException();
            }

            args.put(keyValue[0], keyValue[1]);
        }

        return args;
    }

    private static boolean validateArgumentValue(String[] keyValue) {
        if (!argumentValidators.containsKey(keyValue[0])) {
            return true;
        }

        return Pattern.compile(argumentValidators.get(keyValue[0])).matcher(keyValue[1]).matches();
    }

    private static <T> boolean contains(T[] array, T value) {
        return Arrays.asList(array).contains(value);
    }
}
