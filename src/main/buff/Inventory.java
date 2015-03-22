package buff;

import inspector.Inspector;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;

public class Inventory<T extends Buff> {
    private final ArrayList<T> inventory;

    public Inventory() {
        Inspector.call("Inventory.Inventory()");
        inventory = new ArrayList<T>();
        Inspector.ret("Inventory.Inventory");
    }

    public void addItem(T buff) {
        Inspector.call("Inventory.addItem(T)");
        inventory.add(buff);
        Inspector.call("Inventory.addItem");
    }

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
