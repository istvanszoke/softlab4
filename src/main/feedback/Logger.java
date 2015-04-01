package feedback;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Logger {
    private static LogLevel logLevel;

    public static LogLevel getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(LogLevel newLevel) {
        logLevel = newLevel;
    }

    public static void log(Result result) {
        if (logLevel == LogLevel.OFF) {
            return;
        }

        SortedMap<Integer, String> merged = new TreeMap<Integer, String>();
        if (logLevel == LogLevel.NORMAL) {
            merged.putAll(result.getNormal());
        } else if (logLevel == LogLevel.TEST) {
            merged.putAll(result.getDebug());
        }

        for (Map.Entry<Integer, String> message : merged.entrySet()) {
            System.out.println(message);
        }
    }
}
