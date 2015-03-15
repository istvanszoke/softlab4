import javax.swing.*;
import java.awt.*;

import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.Player;

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
        int roundTime = 10;

        Game game = new GameCreator()
                .setRoundTime(roundTime)
                .addPlayer(Player.createRobot(roundTime))
                .addPlayer(Player.createRobot(roundTime))
                .generateTestMap(10, 10)
                .create();
        game.registerController(this);
        game.start();
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
