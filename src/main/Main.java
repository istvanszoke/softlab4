import game.Game;
import game.KeyDispatcher;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private void createAndShowGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello Softlab 4");
        getContentPane().add(label);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

        pack();
        setVisible(true);
    }

    private void gameLoop() {
        final Game game = new Game();
        game.registerController(this);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    game.process();
                }
            }
        });

        t.run();
    }

    public static void main(String[] args) {
        final Main main = new Main();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.createAndShowGUI();
            }
        });
        main.gameLoop();
    }
}
