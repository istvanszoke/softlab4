package feedback;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Result {
    private final SortedMap<Integer, String> normal;
    private final SortedMap<Integer, String> debug;

    private int counter;
    private boolean isSuccessful;

    public Result() {
        normal = new TreeMap<Integer, String>();
        debug = new TreeMap<Integer, String>() {};
        counter = 0;
        isSuccessful = true;
    }

    public synchronized void pushNormal(String message) {
        normal.put(counter, message);
        counter += 1;
    }

    public synchronized void pushDebug(String message) {
        debug.put(counter, message);
        counter += 1;
    }

    public synchronized void setFailed() {
        isSuccessful = false;
    }

    public synchronized boolean isSuccessful() {
        return isSuccessful;
    }

    public synchronized SortedMap<Integer, String> getNormal() {
        return normal;
    }

    public synchronized SortedMap<Integer, String> getDebug() {
        return debug;
    }

    public synchronized void append(Result result) {
        for (String entry : result.getNormal().values()) {
            pushNormal(entry);
        }

        for (String entry : result.getDebug().values()) {
            pushDebug(entry);
        }
    }
}
