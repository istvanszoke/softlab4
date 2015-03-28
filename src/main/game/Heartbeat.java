package game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Heartbeat {
    public static final int RESOLUTION = 500  ;

    private static ArrayList<HeartbeatListener> listeners;
    private static long elapsedTime;
    private static boolean isPaused;

    static {
        listeners = new ArrayList<HeartbeatListener>();
        elapsedTime = 0;
        Timer timer = new Timer(true);
        isPaused = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isPaused()) {
                    return;
                }

                elapsedTime += RESOLUTION;
                for (HeartbeatListener listener : listeners) {
                    listener.onTick(RESOLUTION);
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
        listeners.add(listener);
    }

    public static void unsubscribe(HeartbeatListener listener) {
        listeners.remove(listener);
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}
