import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.KeyboardFocusManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import game.Game;
import game.GameCreator;
import game.KeyDispatcher;
import game.handle.PlayerHandle;
import proto.CommandParser;
import proto.InvalidCommandArgumentException;
import proto.InvalidCommandException;
import proto.ProtoCommand;

public class Main extends JFrame {
    public static void main(String[] args) throws IOException {
        // You can use the kilep() command to proceed with the game testing
        testInput();
        testGame();
    }

    private static void testInput() throws IOException {
        ProtoCommand command = new ProtoCommand();

        while (!command.getCommand().equals(ProtoCommand.EXIT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            try {
                command = CommandParser.parse(line);
                System.out.println(command.getCommand() + ": ");
                for (Map.Entry<String, String> arg : command.getArgs().entrySet()) {
                    System.out.println("    " + arg.getKey() + ": " + arg.getValue());
                }
                System.out.println();

            } catch (InvalidCommandException ignored) {
                System.out.println("Nem helyes parancs");
            } catch (InvalidCommandArgumentException e) {
                System.out.println("Rossz argumentum");
            }
        }
    }

    private static void testGame() {
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

        Game game = new GameCreator().generateMap(10, 10)
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
