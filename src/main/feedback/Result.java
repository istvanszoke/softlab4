package feedback;

import inspector.Inspector;

import java.util.ArrayList;

public class Result {
    private final ArrayList<String> messages;

    public Result() {
        messages = new ArrayList<String>();/**/
    }

    public void pushMessage(String message) {
        Inspector.call("Result.pushMessage(String)");
        messages.add(message);
        Inspector.ret("Result.pushMessage");
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
