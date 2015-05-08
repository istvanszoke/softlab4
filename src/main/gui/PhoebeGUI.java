package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;

import game.Game;
import game.KeyDispatcher;
import graphics.GameGraphics;


public class PhoebeGUI extends JFrame
{
    private GameControlPanel gameControlPanel;
    private GameOperationPanel gameOperationPanel;
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

        gameGraphics = new GameGraphics();
        add(gameGraphics, BorderLayout.CENTER);

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        {
            gameControlPanel = new GameControlPanel(this);
            gameOperationPanel = new GameOperationPanel(this);
            controlsPanel.add(gameOperationPanel);
            controlsPanel.add(gameControlPanel);
        }
        add(controlsPanel, BorderLayout.LINE_END);

        pack();
    }

    void startGame(Game game) {

    }

    void stopGame() {

    }

    public void createAndShowGUI() {
        setVisible(true);
    }
}
