package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GameOperationPanel extends JPanel
{
    private PhoebeGUI mainFrame;

    private JComboBox gNumOfPlayersCmb;
    private JComboBox gPlayerTimeCmb;
    private JButton gLoadMatBtn;
    private JButton gStartGameBtn;
    private JButton gPauseGameBtn;
    private JButton gStopGameBtn;

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
        gLoadMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Térkép fájl", "map"));
                fileChooser.showDialog(null, "Betöltés");
                loadedFile = fileChooser.getSelectedFile();
                if (loadedFile != null && loadedFile.exists()) {
                    gStartGameBtn.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Nincs ilyen fájl", "Hiba", JOptionPane.ERROR_MESSAGE);
                    gStartGameBtn.setEnabled(false);
                }
            }
        });

        gStartGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mainFrame.startNewGame(loadedFile,
                                           (Integer)gNumOfPlayersCmb.getSelectedItem(),
                                           (Integer)gPlayerTimeCmb.getSelectedItem())) {
                    gStopGameBtn.setEnabled(true);
                    gPauseGameBtn.setEnabled(true);
                    gStartGameBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Hiba történt indításkor", "Hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gStopGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.stopGame();
                gameStopped();
            }
        });

        gPauseGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mainFrame.tooglePause()) {
                    if (gPauseGameBtn.getText().equals("Játék felfüggesztése")) {
                        gPauseGameBtn.setText("Játék folytatása");
                    } else if (gPauseGameBtn.getText().equals("Játék folytatása")) {
                        gPauseGameBtn.setText("Játék felfüggesztése");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hiba történt felfüggesztéskor", "Hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        }

    private void buildPanel() {
        //Temporary control declaration
        JLabel lNumOfPlayersLbl = new JLabel("Játékosok száma", JLabel.LEFT);
        JLabel lPlayerTimeLbl = new JLabel("Játékosok körideje (perc)", JLabel.LEFT);

        JPanel lContPanel = new JPanel();
        Dimension lItemDimension = new Dimension(500,25);

        //Panel initialization
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Játék"));
        //setMaximumSize(new Dimension(250, 200));

        //Global and temporary controls initilaization
        lContPanel.setLayout(new GridLayout(0,1));
        lContPanel.setMaximumSize(new Dimension(250,240));
        {
            gNumOfPlayersCmb = new JComboBox();
            for (int i = 2; i <= 4; ++i) {
                gNumOfPlayersCmb.addItem(i);
            }
            gPlayerTimeCmb = new JComboBox();
            {
                gPlayerTimeCmb.addItem(2);
                gPlayerTimeCmb.addItem(5);
                gPlayerTimeCmb.addItem(7);
                gPlayerTimeCmb.addItem(10);
                gPlayerTimeCmb.addItem(15);
            }
            gLoadMatBtn = new JButton("Pálya betöltése");
            gStartGameBtn = new JButton("Játék indítása");
            {
                gStartGameBtn.setEnabled(false);
            }
            gPauseGameBtn = new JButton("Játék felfüggesztése");
            {
                gPauseGameBtn.setEnabled(false);
            }
            gStopGameBtn = new JButton("Játék megszakítása");
            {
                gStopGameBtn.setEnabled(false);
            }
        }

        lContPanel.add(lNumOfPlayersLbl);
        lContPanel.add(gNumOfPlayersCmb);
        lContPanel.add(lPlayerTimeLbl);
        lContPanel.add(gPlayerTimeCmb);
        lContPanel.add(gLoadMatBtn);
        lContPanel.add(gStartGameBtn);
        lContPanel.add(gPauseGameBtn);
        lContPanel.add(gStopGameBtn);

        add(lContPanel);
    }

    void gameStopped() {
        gPauseGameBtn.setEnabled(false);
        gStopGameBtn.setEnabled(false);
        gStartGameBtn.setEnabled(true);
    }

}
