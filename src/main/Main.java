import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.KeyboardFocusManager;

import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.handle.PlayerHandle;

public class Main extends JFrame {
    public static void main(String[] args) {
        final Main main = new Main();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.createAndShowGUI();
            }
        });
        main.gameLoop();
    }

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
        int roundTime = 10;

        Game game = new GameCreator().generateTestMap(10, 10)
                .addAgent(PlayerHandle.createRobot(roundTime))
                .addAgent(PlayerHandle.createRobot(roundTime))
                .create();

        if (game == null) {
            System.out.println("Game creation was unsuccessful");
        } else {
            game.registerController(this);
            game.start();
        }
    }
}
