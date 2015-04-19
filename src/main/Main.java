import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.KeyboardFocusManager;
import java.io.*;
import java.util.*;
import java.util.Map;

import agents.Robot;
import agents.Vacuum;
import buff.Buff;
import buff.Oil;
import buff.Sticky;
import field.Field;
import game.*;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;
import proto.CommandParser;
import proto.InvalidCommandArgumentException;
import proto.InvalidCommandException;
import proto.ProtoCommand;

public class Main extends JFrame implements GameListener {
    public static void main(String[] args) throws IOException {
        // You can use the kilep() command to proceed with the game testing
        //testInput();
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
        final int roundTime = 5;

        // Change these to generate the test cases. Both the agent and buff hashmaps work
        // in a similar fashion: the first entry in put is the actual object we want to place,
        // the second is the field ID we want to place it in. On the test maps this is a single
        // integer, where id(cell(row, col)) = row * MAP_WIDTH + col
        String mapName = "test10.map";
        Game testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 32);
                    put(PlayerHandle.createRobot(roundTime), 35);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});



        //This part is just to test out Serialization
        //I didn't mean this to be a final code
        try {
            FileOutputStream fos = new FileOutputStream("src/resources/maps/" + mapName);
            if (serializeGame(testCase, fos)) {
                fos.flush();
                fos.close();
                fos = null;
                testCase = null;
                System.gc();
                FileInputStream fis = new FileInputStream("src/resources/maps/" + mapName);
                testCase = deserializeGame(fis);
                if (testCase == null)
                    return;
            } else {
                fos.flush();
                fos.close();
                return;
            }
        } catch (IOException ex) {

        }
        //-----------------------------------------------------------------------

        if (testCase == null) {
            System.out.println("Game creation was unsuccessful");
        } else {
            testCase.registerController(this);
            testCase.addListener(this);
            testCase.start();
        }
    }

    @Override
    public void onGameFinished(List<AgentHandle> playerList) {
        System.out.println("Game Over");
        System.out.println("Placements: ");

        Collections.sort(playerList, new Comparator<AgentHandle>() {
            @Override
            public int compare(AgentHandle o1, AgentHandle o2) {
                if (o1.getAgent().isDead() && o2.getAgent().isDead()) {
                    return 0;
                }

                if (o1.getAgent().isDead()) {
                    return 1;
                }

                if (o2.getAgent().isDead()) {
                    return -1;
                }

                if (o1.getAgent().getLap() < o2.getAgent().getLap()) {
                    return 1;
                } else if (o1.getAgent().getLap() > o2.getAgent().getLap()) {
                    return -1;
                }

                int firstDistance = o1.getAgent().getField().getDistanceFromGoal();
                int secondDistance = o1.getAgent().getField().getDistanceFromGoal();

                return secondDistance - firstDistance;
            }
        });

        for (AgentHandle handle : playerList) {
            System.out.println(handle + " " +
                               (handle.getAgent().isDead() ? "dead " : "alive ") +
                               "distance: " + handle.getAgent().getField().getDistanceFromGoal());
        }
    }

    public boolean serializeGame(Game gameToSerialize, OutputStream output) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(output);

            oos.writeObject(gameToSerialize.getGameStorage());
            oos.writeObject(gameToSerialize.getMap());
            Robot.writeStaticParams(oos);
            Vacuum.writeStaticParams(oos);
            Field.writeStaticParams(oos);
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    public Game deserializeGame(InputStream input) {
        try {
            ObjectInputStream ois = new ObjectInputStream(input);
            GameStorage restoredGameStorage = (GameStorage)ois.readObject();
            game.Map restoredMap = (game.Map)ois.readObject();
            Robot.readStaticParams(ois);
            Vacuum.readStaticParams(ois);
            Field.readStaticParams(ois);
            return new Game(restoredGameStorage, restoredMap);
        } catch (IOException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
}
