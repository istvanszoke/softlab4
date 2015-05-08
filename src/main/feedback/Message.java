package feedback;

public class Message {
    private final String message;
    private final LogLevel level;

    public Message(String message, LogLevel level) {
        this.message = message;
        this.level = level;
    }

    public final String getMessage() {
        return message;
    }

    public final LogLevel getLevel() {
        return level;
    }
}
