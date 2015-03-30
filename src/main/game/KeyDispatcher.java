package game;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class KeyDispatcher implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        return false;
    }
}
