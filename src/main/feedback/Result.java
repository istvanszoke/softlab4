package feedback;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Egy tesznek egy futási eredménye
 */
public class Result {
    /**
     * Normál tesztesetek eredményeinek tárolása
     */
    private final SortedMap<Integer, String> normal;
    /**
     * Hibakeresési tesztesetek eredményeinek tárolása
     */
    private final SortedMap<Integer, String> debug;

    /**
     * Tárolt eredmények
     */
    private int counter;
    /**
     * Összeségébe sikeres eredmény-e
     */
    private boolean isSuccessful;

    /**
     * Eredménynek a kostruktora
     */
    public Result() {
        normal = new TreeMap<Integer, String>();
        debug = new TreeMap<Integer, String>() {};
        counter = 0;
        isSuccessful = true;
    }

    /**
     * Üzenet hozzáadása a normál tesztesetek eredményeihez
     * @param message - A hozzáadandó üzenet
     */
    public synchronized void pushNormal(String message) {
        normal.put(counter, message);
        counter += 1;
    }

    /**
     * Üzenet hozzáadása a hibekeresési tesztesetek eredményihez
     * @param message
     */
    public synchronized void pushDebug(String message) {
        debug.put(counter, message);
        counter += 1;
    }

    /**
     * Sikertelen eredmény beállítása
     */
    public synchronized void setFailed() {
        isSuccessful = false;
    }

    /**
     * Sikeresség lekérése
     * @return - A sikeresség
     */
    public synchronized boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Normál eredmény elemeinek lekérése
     * @return - Lekért elemek
     */
    public synchronized SortedMap<Integer, String> getNormal() {
        return normal;
    }

    /**
     * Hibakeresési eredmény elemeinek lekérése
     * @return - Lekért elemek
     */
    public synchronized SortedMap<Integer, String> getDebug() {
        return debug;
    }
}
