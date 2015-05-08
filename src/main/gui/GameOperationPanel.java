package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

public class GameOperationPanel extends JPanel
{
    private PhoebeGUI mainFrame;

    private JComboBox<Integer> gNumOfPlayersCmb;
    private JButton gLoadMatBtn;
    private JButton gStartGameBtn;
    private JButton gPauseGameBtn;
    private JButton gResetGameBtn;

    private File loadedFile;

    GameOperationPanel(PhoebeGUI mainFrame) {
        if (mainFrame != null) {
            this.mainFrame = mainFrame;
            buildPanel();
            setEventListeners();
        } else {
            throw new NullPointerException();
        }
    }

    private void setEventListeners() {

    }

    private void buildPanel() {
        //Temporary control declaration
        JLabel lNumOfPlayersLbl = new JLabel("Játékosok száma", JLabel.LEFT);
        JPanel lContPanel = new JPanel();
        Dimension lItemDimension = new Dimension(500,25);

        //Panel initialization
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Játék"));
        //setMaximumSize(new Dimension(250, 200));

        //Global and temporary controls initilaization
        lContPanel.setLayout(new GridLayout(6,1));
        lContPanel.setMaximumSize(new Dimension(250,180));
        {
            gNumOfPlayersCmb = new JComboBox<Integer>();
            for (int i = 2; i <= 4; ++i) {
                gNumOfPlayersCmb.addItem(i);
            }
            gLoadMatBtn = new JButton("Pálya betöltése");
            gStartGameBtn = new JButton("Játék indítása");
            gPauseGameBtn = new JButton("Játék felfüggesztése");
            gResetGameBtn = new JButton("Játék megszakítása");
        }

        lContPanel.add(lNumOfPlayersLbl);
        lContPanel.add(gNumOfPlayersCmb);
        lContPanel.add(gLoadMatBtn);
        lContPanel.add(gStartGameBtn);
        lContPanel.add(gPauseGameBtn);
        lContPanel.add(gResetGameBtn);

        add(lContPanel);
    }
}
