package buff;

import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;

/**
 * Buffok tárolására alkalmas tároló.
 * Számon tartja az elérhető buffok számát, így csak akkor használható, amikor nem üres.
 */
public class Inventory<T extends Buff> {
    /**
     * Buffokat tartalmazó gyűjtemény.
     */
    private final ArrayList<T> inventory;

    /**
     * Konstruktor
     */
    public Inventory() {
        Inspector.call("Inventory.Inventory()");
        inventory = new ArrayList<T>();
        Inspector.ret("Inventory.Inventory");
    }

    /**
     * Buff hozzáadása a gyűjteményhez.
     * @param buff - A hozzáadásra kerülő buff
     */
    public void addItem(T buff) {
        Inspector.call("Inventory.addItem(T)");
        inventory.add(buff);
        Inspector.call("Inventory.addItem");
    }

    /**
     * Használja a gyűjteményben tárolt elemek valamelyikét.
     * @return - Amennyiben nem üres a gyűjtemény igazzal tér vissza, egyébként hamis.
     */
    public boolean useItem() {
        Inspector.call("Inventory.useItem():boolean");
        if (inventory.isEmpty()) {
            Inspector.ret("Inventory.useItem");
            return false;
        }

        inventory.remove(inventory.size() - 1);
        Inspector.ret("Inventory.useItem");
        return true;
    }
}
