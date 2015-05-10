package buff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends Buff> implements Serializable {

    private static final long serialVersionUID = -1042277609272860020L;

    private final List<T> inventory;

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

    public int getItemCount() {
        return inventory.size();
    }
}
