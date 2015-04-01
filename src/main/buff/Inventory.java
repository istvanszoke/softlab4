package buff;

import java.util.ArrayList;
import java.util.List;

/**
 * Buffok tárolására alkalmas tároló.
 * Számon tartja az elérhető buffok számát, így csak akkor használható, amikor nem üres.
 */
public class Inventory<T extends Buff> {
    /**
     * Buffokat tartalmazó gyűjtemény.
     */
    private final List<T> inventory;

    /**
     * Konstruktor
     */
    public Inventory() {
        inventory = new ArrayList<T>();
    }

    /**
     * Buff hozzáadása a gyűjteményhez.
     * @param buff - A hozzáadásra kerülő buff
     */
    public void addItem(T buff) {
        inventory.add(buff);
    }

    /**
     * Használja a gyűjteményben tárolt elemek valamelyikét.
     * @return - Amennyiben nem üres a gyűjtemény igazzal tér vissza, egyébként hamis.
     */
    public boolean useItem() {
        if (inventory.isEmpty()) {
            return false;
        }

        inventory.remove(inventory.size() - 1);
        return true;
    }
}
