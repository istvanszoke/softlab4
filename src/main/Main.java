import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import field.Direction;
import game.*;
import game.control.ProtoCommandController;
import game.handle.AgentHandle;
import gui.PhoebeGUI;
import proto.*;
import util.BitmapToMapGenerator;

public class Main implements GameListener {
    private enum OperationMode {
        STDIO,
        GUI
    }

    OperationMode opMode;
    Game mainGame;
    PhoebeGUI phoebeGUI;
    ProtoCommandController protoController = new ProtoCommandController();

    public static void main(String[] args) throws IOException {
        //TestcaseGenerator.generateTestCases(30);
        boolean stdio = false;

        for(String item : args) {
            if (item.contains("--stdio")) {
                stdio = true;
                break;
            } else if (item.contains("--mapgen")) {
                int index = item.indexOf("--mapgen");
                generateMapFromBitmap(args[index+1], args[index+2], args[index+3], args[index+4]);
                return;
            }
        }

        Main main = new Main (stdio ? OperationMode.STDIO : OperationMode.GUI);
        main.start();
    }

    private static void generateMapFromBitmap(String path, String startDir, String agentRoundTime, String oilTimeout) {
        File inputFile = new File(path);
        File destFile = new File("src/resources/"+inputFile.getName()+".map");
        Direction dir;
        int roundTime;
        if (!inputFile.exists())
            return;
        if (startDir.equals("UP")) dir = Direction.UP;
        else if (startDir.equals("DOWN")) dir = Direction.DOWN;
        else if (startDir.equals("LEFT")) dir = Direction.LEFT;
        else if (startDir.equals("RIGHT")) dir = Direction.RIGHT;
        else return;
        roundTime = Integer.parseInt(agentRoundTime);
        int oilTime = Integer.parseInt(oilTimeout);
        if (roundTime < 0)
            return;
        try {
            BitmapToMapGenerator.generateMapToFile(ImageIO.read(inputFile),destFile,dir,roundTime, oilTime);
        } catch (IOException e) {
            return;
        }

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
            } catch (Exception ignored) {

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
                    mainGame.initialize(protoController);
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
            } else if (!protoController.processProtoCommand(command)) {
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

    @Override
    public void onAgentChange() {

    }
}
