package feedback;

import java.util.*;

public class Result {
    private final List<Message> messages;

    public Result() {
        messages = new ArrayList<Message>();
    }

    public synchronized void pushNormal(String message) {
        messages.add(new Message(message, LogLevel.NORMAL));
    }

    public synchronized void pushDebug(String message) {
        messages.add(new Message(message, LogLevel.DEBUG));
    }

    public synchronized List<Message> getMessages() {
        return messages;
    }
}
