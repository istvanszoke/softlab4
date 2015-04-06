package proto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Teszteléshez szükséges utasításfeldolgozó
 */
public class CommandParser {
    /**
     * Elfogadott parancsok hozzárendelései
     */
    //TODO: Change the argument of betolt() and ment() from nev to allas in the docs.
    private static final Map<String, String[]> acceptedCommands = Collections.unmodifiableMap(new HashMap<String, String[]>() {{
        put(ProtoCommand.PLAY, new String[]{"szam", "ido", "palya"});
        put(ProtoCommand.LOAD, new String[]{"allas"});
        put(ProtoCommand.SAVE, new String[]{"allas"});
        put(ProtoCommand.JUMP, new String[]{});
        put(ProtoCommand.CHANGE_DIR, new String[]{"irany"});
        put(ProtoCommand.CHANGE_SPEED, new String[]{"delta"});
        put(ProtoCommand.USE_OIL, new String[]{});
        put(ProtoCommand.USE_STICKY, new String[]{});
        put(ProtoCommand.EXIT, new String[]{});
    }});
    /**
     * Argumentumelleőrzők
     */
    private static final Map<String, String> argumentValidators = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("szam", "[2-4]");
        put("ido", "[0-3]:[0-5][0-9]");
        put("palya", "[A-Za-z0-9]+\\.map");
        put("allas", "[A-Za-z0-9]+\\.sav");
        put("irany", "(FEL|LE|BAL|JOBB)");
        put("delta", "[+-]1");
    }});

    /**
     * Elpállít a PrototípusParancsot a bekért szövegből
     *
     * @param commandString - Bekérsz parancs szövege
     * @return Az előállított prototípus parancs
     * @throws InvalidCommandException         - Érvénytelen parancs
     * @throws InvalidCommandArgumentException - Érvénytelen parancs argumentum
     */
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

    /**
     * Argumentumok feloldása
     *
     * @param command - Parancs szövege
     * @param rawArgs - Nyers argumentumok
     * @return - Feldoldott argumentumok
     * @throws InvalidCommandArgumentException - Érvénytelen argumentum
     */
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

    /**
     * Argumentumérték validáslása
     *
     * @param keyValue - Validálandó argumentum kulcs
     * @return - Validálás eredménye
     */
    private static boolean validateArgumentValue(String[] keyValue) {
        if (!argumentValidators.containsKey(keyValue[0])) {
            return true;
        }

        return Pattern.compile(argumentValidators.get(keyValue[0])).matcher(keyValue[1]).matches();
    }

    /**
     * Tömbben kereső
     *
     * @param array - Tömb
     * @param value - Érték
     * @param <T>   - A tömb típusa
     * @return - Mely tömbelemek tartalmazzák az értéket
     */
    private static <T> boolean contains(T[] array, T value) {
        return Arrays.asList(array).contains(value);
    }
}
