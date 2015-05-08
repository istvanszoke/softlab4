package gui;

import javax.swing.*;
import java.awt.*;

import game.Heartbeat;
import game.HeartbeatListener;

public class GameControlPanel extends JPanel implements HeartbeatListener
{
    private PhoebeGUI mainFrame;

    private JButton gZoomInBtn;
    private JButton gZoomOutBtn;
    private JButton gDirectionUpBtn;
    private JButton gDirectionDownBtn;
    private JButton gDirectionRightBtn;
    private JButton gDirectionLeftBtn;
    private JButton gJumpBtn;
    private JButton gIncreaseSpeedBtn;
    private JButton gDecreaseSpeedBtn;
    private JButton gPlaceOilBtn;
    private JButton gPlaceStickyBtn;


    private JLabel gSpeedLbl;
    private JLabel gPlayerTimeLeftLbl;
    private JLabel gTotalTimeLeftLbl;


    GameControlPanel(PhoebeGUI mainFrame) {
        if (mainFrame != null) {
            this.mainFrame = mainFrame;
            buildPanel();
            setEventListeners();
            Heartbeat.subscribe(this);
        } else {
            throw new NullPointerException();
        }
    }

    private void setEventListeners()
    {

    }

    private void buildPanel() {
        gZoomInBtn = new JButton("+");
        gZoomOutBtn = new JButton("-");
        gDirectionUpBtn = new JButton("\u25B2");
        gDirectionDownBtn = new JButton("\u25BC");
        gDirectionRightBtn = new JButton("\u25B6");
        gDirectionLeftBtn = new JButton("\u25C0");
        gJumpBtn = new JButton("Ugrik");
        gIncreaseSpeedBtn = new JButton("+");
        gDecreaseSpeedBtn = new JButton("-");
        gPlaceOilBtn = new JButton("0");
        gPlaceStickyBtn = new JButton("0");

        gSpeedLbl = new JLabel();


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Vezérlés"));

        JPanel lZoomControls = new JPanel();
        lZoomControls.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lZoomControls.add(gZoomOutBtn);
        lZoomControls.add(new JLabel("Zoom", JLabel.CENTER));
        lZoomControls.add(gZoomInBtn);
        add(lZoomControls);

        JPanel lDirectionControls = new JPanel();
        lDirectionControls.setBorder(BorderFactory.createTitledBorder(
                                     BorderFactory.createLineBorder(Color.BLACK),"Irányítás"));
        lDirectionControls.setLayout(new GridLayout(3,3));
        lDirectionControls.add(new JPanel());
        lDirectionControls.add(gDirectionUpBtn);
        lDirectionControls.add(new JPanel());
        lDirectionControls.add(gDirectionLeftBtn);
        lDirectionControls.add(gJumpBtn);
        lDirectionControls.add(gDirectionRightBtn);
        lDirectionControls.add(new JPanel());
        lDirectionControls.add(gDirectionDownBtn);
        lDirectionControls.add(new JPanel());
        add(lDirectionControls);

        JPanel lSpeedControls = new JPanel();
        lSpeedControls.setLayout(new BoxLayout(lSpeedControls, BoxLayout.Y_AXIS));
        lSpeedControls.setBorder(BorderFactory.createTitledBorder(
                                 BorderFactory.createLineBorder(Color.BLACK),"Sebesség"));
        {
            JPanel lActualSpeedControls = new JPanel();
            lActualSpeedControls.add(new JLabel("Aktuális sebesség:", JLabel.CENTER));
            lSpeedControls.add(lActualSpeedControls);
        }
        lSpeedControls.add(gSpeedLbl);
        {
            JPanel lSpeedChangeControls = new JPanel();
            lSpeedChangeControls.add(gDecreaseSpeedBtn);
            lSpeedChangeControls.add(new JLabel("Változtat", JLabel.CENTER));
            lSpeedChangeControls.add(gIncreaseSpeedBtn);
            lSpeedControls.add(lSpeedChangeControls);
        }
        add(lSpeedControls);

        JPanel lBuffControls = new JPanel();
        lBuffControls.setBorder(BorderFactory.createTitledBorder(
                                 BorderFactory.createLineBorder(Color.BLACK),"Támadás"));
        lBuffControls.setLayout(new GridLayout(0,1));
        {
            JPanel lOilControl = new JPanel();
            lOilControl.setLayout(new GridLayout(1,0));
            lOilControl.add(new JLabel("Olaj: "));
            lOilControl.add(gPlaceOilBtn);
            lBuffControls.add(lOilControl);
        }
        {
            JPanel lStickyControl = new JPanel();
            lStickyControl.setLayout(new GridLayout(1,0));
            lStickyControl.add(new JLabel("Ragacs: "));
            lStickyControl.add(gPlaceStickyBtn);
            lBuffControls.add(lStickyControl);
        }
        add(lBuffControls);

    }

    @Override
    public void onTick(long deltaTime) {

    }
}
