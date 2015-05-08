package gui;

import javax.swing.JPanel;

public class GameControlPanel extends JPanel
{
    private PhoebeGUI mainFrame;

    GameControlPanel(PhoebeGUI mainFrame) {
        if (mainFrame != null) {
            this.mainFrame = mainFrame;
            buildPanel();
        } else {
            throw new NullPointerException();
        }
    }

    private void buildPanel() {

    }
}
