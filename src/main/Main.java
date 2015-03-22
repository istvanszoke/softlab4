import javax.swing.*;
import java.awt.*;

import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.Player;

/** Ez az osztály az alkalmazásnak a főosztálya
 * Feladaa a kezelőfelület valamint a játéklogika elindítás
 */
public class Main extends JFrame {
    /**
     * Program belépési függvénye
     * Ez a fügvény képezi az alkalmazásnak a belépési pontját melyet a JVM
     * hív meg amikor elindítjuk az alkalmazást
     * @param args - Paraméterek a program meghívásakor. Figyelmen kívül vannak hagyva
     */
    public static void main(String[] args) {
        final Main main = new Main();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.createAndShowGUI();
            }
        });
        main.gameLoop();
    }

    /**
     * Kezelői felületet létrehozó függvény
     * Feladata, hogy elindítja a kezelőfelületet az alkalmazásban
     */
    private void createAndShowGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello Softlab 4");
        getContentPane().add(label);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

        pack();
        setVisible(true);
    }

    /**
     * Játék létrehozása és elindítása
     * Feladata, hogy létrehozza a játéklogikát képviselő objektumot.
     */
    private void gameLoop() {
        int roundTime = 10;

        Game game = new GameCreator()
                .setRoundTime(roundTime)
                .addPlayer(Player.createRobot(roundTime))
                .addPlayer(Player.createRobot(roundTime))
                .generateTestMap(10, 10)
                .create();

        if (game == null) {
            System.out.println("Game creation was unsuccessful");
        } else {
            game.registerController(this);
            game.start();
        }
    }
}
