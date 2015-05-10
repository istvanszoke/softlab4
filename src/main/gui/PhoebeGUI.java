package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.*;

import agents.Agent;
import agents.Robot;
import game.*;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import graphics.GameGraphics;
import graphics.handles.RobotSprite;


public class PhoebeGUI extends JFrame implements GameListener, HeartbeatListener
{
    private GameOperationPanel gameOperationPanel;
    private GameControlPanel gameControlPanel;
    private JPanel controlsPanel;
    private GameGraphics gameGraphics;
    private Game mainGame;
    private boolean isPaused = false;
    private int zoom = 5;

    public PhoebeGUI () {
        super();
        buildGui();
    }

    private void buildGui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Phoebe game");
        setMinimumSize(new Dimension(800, 500));

        gameGraphics = new GameGraphics();
        add(gameGraphics, BorderLayout.CENTER);

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        {
            gameOperationPanel = new GameOperationPanel(this);
            gameControlPanel = new GameControlPanel();
            controlsPanel.add(gameOperationPanel);
            controlsPanel.add(gameControlPanel);
        }
        add(controlsPanel, BorderLayout.LINE_END);

        pack();
    }

    boolean startNewGame(File map, int playerCount, int playerTime) {
        game.Map loadedMap = GameSerializer.loadMap(map);
        List<AgentHandle> players = new ArrayList<AgentHandle>();
        for (int i = 0; i < playerCount; ++i) {
            AgentHandle agentHandle = new PlayerHandle(new Robot(), playerTime * 60);
            players.add(agentHandle);
        }


        GameControlPanel oldControlPanel = gameControlPanel;
        controlsPanel.remove(oldControlPanel);
        gameControlPanel = new GameControlPanel();
        controlsPanel.add(gameControlPanel);

        Game newGame;
        try {
            newGame = new Game(players, loadedMap);
        } catch (BadMapSizeException e) {
            controlsPanel.remove(gameControlPanel);
            gameControlPanel = oldControlPanel;
            controlsPanel.add(oldControlPanel);
            JOptionPane.showMessageDialog(this,
                                          "Az adott pálya nem kompatibilis a kiválasztott játékosok számával.\n" +
                                          "Maximális játékosszám: " + e.getMaxSize() + "\n" +
                                          "Aktuális játékosszám: " + e.getNumPlayers(),
                                          "Rossz pályaméret",
                                          JOptionPane.WARNING_MESSAGE);
            return false;
        }

        mainGame = newGame;
        mainGame.addListener(this);
        mainGame.initialize(gameControlPanel);

        gameControlPanel.setMainFrame(this);
        gameGraphics.attachToMap(mainGame.getMap());

        mainGame.start();
        isPaused = false;

        pack();
        refreshGraphics();
        Heartbeat.subscribe(this);
        return true;
    }

    boolean stopGame() {
        mainGame.removeListener(this);
        mainGame.pause();
        mainGame = null;
        controlsPanel.remove(gameControlPanel);
        Heartbeat.unsubscribe(gameControlPanel);
        gameControlPanel = new GameControlPanel();
        controlsPanel.add(gameControlPanel);
        pack();
        isPaused = false;
        Heartbeat.unsubscribe(this);
        return true;
    }

    boolean togglePause() {
        if (mainGame != null) {
            if (!isPaused) {
                mainGame.pause();
                gameControlPanel.setEnabled(false);
                isPaused = true;
            } else {
                mainGame.start();
                gameControlPanel.setEnabled(true);
                isPaused = false;
            }
            return true;
        } else {
            return false;
        }
    }

    Game getGame() {
        return mainGame;
    }

    void zoomInMap() {
        if (zoom > 1) {
            --zoom;
            refreshGraphics();
        }
    }

    void zoomOutMap() {
        if (zoom < 100) {
            ++zoom;
            refreshGraphics();
        }
    }

    void refreshGraphics() {
        Agent agent;
        try {
            agent = gameControlPanel.getCurrentAgent();
        } catch (NoSuchElementException ex) {
            return;
        }
        if (agent != null)
            gameGraphics.centerFieldTo(agent.getField(), zoom);
    }

    public void createAndShowGUI() {
        setVisible(true);
    }

    @Override
    public void onGameFinished(List<AgentHandle> playerList) {
        stopGame();
        gameOperationPanel.gameStopped();

        StringBuilder output = new StringBuilder();
        output.append("Game Over\nHelyezések:\n");

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

                // In the fields we are storing the reverse of the distance we actually need
                return firstDistance - secondDistance;
            }
        });

        java.util.Map<Agent,String> names = RobotSprite.getAgentMapping();
        int place = 0;
        for (AgentHandle handle : playerList) {
            String stateString;
            if (handle.getAgent().isDead()) {
                stateString = "halott";
            } else {
                output.append(++place).append(". ");
                stateString = "él, körök: " + handle.getAgent().getLap() +
                              ", távolság a céltól: " + handle.getAgent().getField().getDistanceFromGoal();
            }

            output.append(names.get(handle.getAgent()))
                    .append(": ")
                    .append(stateString)
                    .append(String.format("%n"));
        }

        JOptionPane.showMessageDialog(this, output.toString(), "Játék vége", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onAgentChange() {
        refreshGraphics();
    }

    @Override
    public void onTick(long deltaTime) {
        if (!isPaused) {
            gameGraphics.regenerateImage();
        }
    }
}
