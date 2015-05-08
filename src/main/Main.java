import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.KeyboardFocusManager;
import java.io.*;
import java.util.*;

import game.*;
import game.handle.AgentHandle;
import gui.PhoebeGUI;
import proto.*;

public class Main implements GameListener {

    private static final long serialVersionUID = -6767044297674099347L;

    private enum OperationMode {
        STDIO,
        GUI
    }

    OperationMode opMode;
    Game mainGame;
    PhoebeGUI phoebeGUI;

    public static void main(String[] args) throws IOException {
        TestcaseGenerator.generateTestCases(30);
        boolean stdio = false;

        for(String item : args) {
            if (item.contains("--stdio")) {
                stdio = true;
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
        phoebeGUI = new PhoebeGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                phoebeGUI.createAndShowGUI();
            }
        });
        gameLoop();
    }

    private void operateStdIO() {
        ProtoCommand command = new ProtoCommand();
        Heartbeat.manualize();
        Game.controllerType = Game.ControllerType.PROTOCOMMAND;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!command.getCommand().equals(ProtoCommand.EXIT)) {

            try {
                String line = reader.readLine();
                if (line == null) {
                    continue;
                }

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

                mainGame = GameSerializer.load(new File("src/resources/maps/" + mapName));

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
            } else if (!mainGame.getProtoCommandController().processProtoCommand(command)) {
				System.out.println("Something wrong with command");
            }
        }
    }

    private void gameLoop() {

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
