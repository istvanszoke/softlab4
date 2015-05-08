package feedback;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Logger {
    private static LogLevel logLevel;

    static {
        logLevel = LogLevel.NORMAL;
    }

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

        List<Message> messages = result.getMessages();

        StringBuilder sb = new StringBuilder();
        if (logLevel == LogLevel.NORMAL) {
            for (Message m : messages) {
                if (m.getLevel() == LogLevel.NORMAL) {
                    sb.append(m.getMessage()).append(System.getProperty("line.separator"));
                }
            }
        } else if (logLevel == LogLevel.DEBUG) {
            for (Message m : messages) {
                sb.append(m.getMessage()).append(System.getProperty("line.separator"));
            }
        }
        System.out.print(sb.toString());
    }
}
