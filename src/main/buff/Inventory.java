package buff;

import java.util.ArrayList;

public class Inventory<T extends Buff> {
    private ArrayList<T> inventory = new ArrayList<T>();

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
