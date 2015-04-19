package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Heartbeat {
    public static final int RESOLUTION = 500;

    private static List<HeartbeatListener> listeners;
    private static List<HeartbeatListener> toAdd;
    private static List<HeartbeatListener> toRemove;

    private static long elapsedTime;
    private static boolean isPaused;
    private static boolean isManual;

    static {
        listeners = new ArrayList<HeartbeatListener>();
        toAdd = new ArrayList<HeartbeatListener>();
        toRemove = new ArrayList<HeartbeatListener>();

        elapsedTime = 0;
        Timer timer = new Timer(true);
        isPaused = true;
        isManual = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isManual)
					return;

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
        if (!isManual)
            isPaused = true;
    }

    public synchronized static void resume() {
        if (!isManual)
            isPaused = false;
    }

    public synchronized static void manualize () {
        isPaused = true;
        isManual = true;
    }

    public synchronized static void automatize () {
        isManual = false;
        isPaused = false;
    }

    public static void beat(int diff) {
        if (!isManual)
            return;

        if (!toAdd.isEmpty()) {
            listeners.addAll(toAdd);
            toAdd.clear();
        }

        elapsedTime += diff;
        for (HeartbeatListener listener : listeners) {
            listener.onTick(diff);
        }

        if (!toRemove.isEmpty()) {
            listeners.removeAll(toRemove);
            toRemove.clear();
        }
    }

    public static void beat() {
        beat(RESOLUTION);
    }


    public static void subscribe(HeartbeatListener listener) {
        toAdd.add(listener);
    }

    public static void unsubscribe(HeartbeatListener listener) {
        toRemove.add(listener);
    }

    public static void purgeListeners() {
        listeners.clear();
        toAdd.clear();
        toRemove.clear();
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}
