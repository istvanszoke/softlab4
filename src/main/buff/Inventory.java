package buff;

import java.util.ArrayList;

public class Inventory<T extends Buff> {
    private final ArrayList<T> inventory;

    public Inventory() {
        inventory = new ArrayList<T>();
    }

    public void addItem(T buff) {
        inventory.add(buff);
    }

    public boolean useItem() {
        if (inventory.isEmpty()) {
            return false;
        }

        inventory.remove(inventory.size() - 1);
        return true;
    }
}
