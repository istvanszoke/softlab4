import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.KeyboardFocusManager;
import java.io.*;
import java.util.*;
import java.util.Map;

import game.*;
import game.handle.AgentHandle;
import proto.*;

public class Main extends JFrame implements GameListener {
    private enum OperationMode {
        STDIO,
        GUI
    }

    OperationMode opMode;
    Game mainGame;

    public static void main(String[] args) throws IOException {
        boolean stdio = true;

        for(String item : args) {
            if (item.contains("--gui")) {
                stdio = false;
                break;
            }
        }

        Main main = new Main (stdio ? OperationMode.STDIO : OperationMode.GUI);
        main.start();
    }

    private Main(OperationMode opMode) {
        this.opMode = opMode;
        mainGame = null;
    }

    private void start() {
        switch (opMode) {
            case STDIO:
                operateStdIO();
                break;
            case GUI:
                operateGui();
                break;
        }
    }


    private void operateGui() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        gameLoop();
    }

    private void operateStdIO() {
        ProtoCommand command = new ProtoCommand();
        Heartbeat.manualize();
        Game.controllerType = Game.ControllerType.PROTOCOMMAND;

        while (!command.getCommand().equals(ProtoCommand.EXIT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String line = reader.readLine();
                command = CommandParser.parse(line);
                if (!command.getCommand().equals(ProtoCommand.EXIT))
                    processCommand(command);
                Thread.sleep(1);
            } catch (InvalidCommandException ignored) {
                System.out.println("Nem helyes parancs");
            } catch (InvalidCommandArgumentException e) {
                System.out.println("Rossz argumentum");
            } catch (IOException ex) {

            } catch (InterruptedException e) {

            }


        }
    }

    private void processCommand(ProtoCommand command) {
        String cmd = command.getCommand();

        if (mainGame == null) {
            if (cmd.equals(ProtoCommand.PLAY)) {
                String mapName = command.getArgs().get("palya");
                String filename = "src/resources/maps/" + mapName;

                try {
                    FileInputStream fis = new FileInputStream(filename);
                    mainGame = GameCreator.deserializeGame(fis);
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (mainGame == null) {
                    System.out.println("Game creation was unsuccessful");
                } else {
                    mainGame.addListener(this);
                    mainGame.start();
                }
            } else {
                System.out.println("Nincs betöltött pálya");
            }
        } else {
            if (cmd.equals(ProtoCommand.STEP_HEARTBEAT)) {
                String timeArg = command.getArgs().get("ido");
                if (timeArg == null) {
                    Heartbeat.beat();
                } else {
                    Heartbeat.beat(Integer.parseInt(timeArg));
                }
            } else if (!mainGame.getProtoCommandController().procesProtoCommand(command)) {
				System.out.println("Something wrong with command");
            }
        }
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
        String mapName = "test03.map";
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/resources/maps/" + mapName);
            mainGame = GameCreator.deserializeGame(fis);
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        if (mainGame == null) {
            System.out.println("Game creation was unsuccessful");
        } else {
            mainGame.registerController(this);
            mainGame.addListener(this);
            mainGame.start();
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
}
