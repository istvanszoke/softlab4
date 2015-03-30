package feedback;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private final List<String> messages;

    public Result() {
        messages = new ArrayList<String>();
    }

    public void pushMessage(String message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message);
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
