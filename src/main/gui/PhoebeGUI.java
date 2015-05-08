package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import game.Game;
import game.GameSerializer;
import graphics.GameGraphics;


public class PhoebeGUI extends JFrame
{
    private GameOperationPanel gameOperationPanel;
    private GameControlPanel gameControlPanel;
    private JPanel controlsPanel;
    private GameGraphics gameGraphics;
    private Game mainGame;
    private boolean isPaused = false;

    public PhoebeGUI () {
        super();
        buildGui();
    }

    private void buildGui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Phoebe game");
        setMinimumSize(new Dimension(700,500));

        gameGraphics = new GameGraphics();
        add(gameGraphics, BorderLayout.CENTER);

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        {
            gameOperationPanel = new GameOperationPanel(this);
            gameControlPanel = new GameControlPanel();
            gameControlPanel.setEnabled(false);
            controlsPanel.add(gameOperationPanel);
            controlsPanel.add(gameControlPanel);
        }
        add(controlsPanel, BorderLayout.LINE_END);

        pack();
    }

    boolean startNewGame(File map, int playerCount) {
        mainGame = GameSerializer.load(map);
        if (mainGame != null) {
            controlsPanel.remove(gameControlPanel);
            gameControlPanel = mainGame.getGameControlPanelController();
            add(gameControlPanel);
            isPaused = false;
            return true;
        } else {
            return false;
        }
    }

    boolean stopGame() {
        mainGame = null;
        remove(gameControlPanel);
        gameControlPanel = new GameControlPanel();
        add(gameControlPanel);
        isPaused = false;
        return true;
    }

    boolean tooglePause() {
        if (mainGame != null) {
            if (!isPaused) {
                mainGame.pause();
                isPaused = true;
            } else {
                mainGame.start();
                isPaused = false;
            }
            return true;
        } else {
            return false;
        }
    }

    public void createAndShowGUI() {
        setVisible(true);
    }
}
