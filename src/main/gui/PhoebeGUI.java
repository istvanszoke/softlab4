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
        try {
            mainGame = GameSerializer.load(map);
        } catch (IOException ex) {
            return false;
        }
        controlsPanel.remove(gameControlPanel);
        gameControlPanel = mainGame.getGameControlPanelController();
        add(gameControlPanel);
        return true;
    }

    boolean stopGame() {
        return true;
    }

    boolean tooglePause() {
        return true;
    }

    public void createAndShowGUI() {
        setVisible(true);
    }
}
