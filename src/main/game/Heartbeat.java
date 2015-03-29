package game;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Heartbeat {
    public static final int RESOLUTION = 500  ;

    private static List<HeartbeatListener> listeners;
    private static List<HeartbeatListener> toAdd;
    private static List<HeartbeatListener> toRemove;

    private static long elapsedTime;
    private static boolean isPaused;

    static {
        listeners = new ArrayList<HeartbeatListener>();
        toAdd = new ArrayList<HeartbeatListener>();
        toRemove = new ArrayList<HeartbeatListener>();

        elapsedTime = 0;
        Timer timer = new Timer(true);
        isPaused = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!toAdd.isEmpty()) {
                    listeners.addAll(toAdd);
                    toAdd.clear();
                }

                if (isPaused()) {
                    return;
                }

                elapsedTime += RESOLUTION;
                for (HeartbeatListener listener : listeners) {
                    listener.onTick(RESOLUTION);
                }

                if (!toRemove.isEmpty()) {
                    listeners.removeAll(toRemove);
                    toRemove.clear();
                }
            }
        }, 0, RESOLUTION);
    }

    public synchronized static boolean isPaused() {
        return isPaused;
    }

    public synchronized static void pause() {
        isPaused = true;
    }

    public synchronized static void resume() {
        isPaused = false;
    }

    public static void subscribe(HeartbeatListener listener) {
        toAdd.add(listener);
    }

    public static void unsubscribe(HeartbeatListener listener) {
        toRemove.add(listener);
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}
