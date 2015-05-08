package gui;

import javax.swing.JPanel;

public class GameOperationPanel extends JPanel
{
    private PhoebeGUI mainFrame;

    GameOperationPanel(PhoebeGUI mainFrame) {
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
