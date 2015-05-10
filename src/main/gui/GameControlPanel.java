package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import agents.*;
import commands.AgentCommand;
import commands.queries.*;
import feedback.Logger;
import field.Direction;
import game.Heartbeat;
import game.HeartbeatListener;
import game.control.GameControllerSocket;
import game.control.HumanController;

public class GameControlPanel extends JPanel implements HeartbeatListener, HumanController
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
    private JLabel gPlayerLapsLbl;

    private java.util.List<GameControllerSocket> sockets;

    private agents.Robot currentRobot;
    private GameControllerSocket currentSocket;
    private int robotEnterSpeedMagnitude;

    public GameControlPanel() {
            sockets = new ArrayList<GameControllerSocket>();
            buildPanel();
            setEventListeners();
            setEnabled(false);
    }

    void setMainFrame(PhoebeGUI mainFrame) {
        if (mainFrame != null) {
            this.mainFrame = mainFrame;
            setEnabled(true);
            Heartbeat.subscribe(this);
        } else {
            throw new NullPointerException();
        }
    }

    private void setEventListeners()
    {
        gZoomInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.zoomInMap();
            }
        });

        gZoomOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.zoomOutMap();
            }
        });

        gDirectionUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeDirectionQuery(Direction.UP));
                mainFrame.refreshGraphics();
            }
        });

        gDirectionDownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeDirectionQuery(Direction.DOWN));
                mainFrame.refreshGraphics();
            }
        });

        gDirectionLeftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeDirectionQuery(Direction.LEFT));
                mainFrame.refreshGraphics();
            }
        });

        gDirectionRightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeDirectionQuery(Direction.RIGHT));
                mainFrame.refreshGraphics();
            }
        });

        gJumpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommandAndChangeAgent(new JumpQuery());
            }
        });

        gIncreaseSpeedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeSpeedQuery(1));
                gIncreaseSpeedBtn.setEnabled(currentRobot.getSpeed().getMagnitude() - robotEnterSpeedMagnitude < 1);
                gDecreaseSpeedBtn.setEnabled(currentRobot.getSpeed().getMagnitude() - robotEnterSpeedMagnitude > -1);
                refreshAgentInfo();
            }
        });

        gDecreaseSpeedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new ChangeSpeedQuery(-1));
                gIncreaseSpeedBtn.setEnabled(currentRobot.getSpeed().getMagnitude() - robotEnterSpeedMagnitude < 1);
                gDecreaseSpeedBtn.setEnabled(currentRobot.getSpeed().getMagnitude() - robotEnterSpeedMagnitude > -1);
                refreshAgentInfo();
            }
        });

        gPlaceOilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new UseOilQuery());
                mainFrame.refreshGraphics();
                refreshAgentInfo();
            }
        });

        gPlaceStickyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                useCommand(new UseStickyQuery());
                refreshAgentInfo();
            }
        });
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

        gSpeedLbl = new JLabel("0");
        gPlayerTimeLeftLbl = new JLabel("", JLabel.RIGHT);
        gPlayerLapsLbl = new JLabel("", JLabel.RIGHT);


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

        JPanel lFeedback = new JPanel();
        lFeedback.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Idő"));
        lFeedback.setLayout(new GridLayout(0, 1));
        {
            JPanel lPlayerTime = new JPanel();
            lPlayerTime.setLayout(new GridLayout(1,0));
            lPlayerTime.add(new JLabel("Hátralévő (mp):"));
            lPlayerTime.add(gPlayerTimeLeftLbl);
            lFeedback.add(lPlayerTime);

            JPanel lPlayerLaps = new JPanel();
            lPlayerLaps.setLayout(new GridLayout(1,0));
            lPlayerLaps.add(new JLabel("Megtett körök: "));
            lPlayerLaps.add(gPlayerLapsLbl);
            lFeedback.add(lPlayerLaps);
        }
        add(lFeedback);

    }

    private void changeCurrentSocket (GameControllerSocket socket) {
        if (socket != null) {
            currentSocket = socket;
            IdentificationQuery identificationQuery = new IdentificationQuery();
            socket.sendAgentCommand(identificationQuery);
            currentRobot = identificationQuery.getIdentifiedRobot();
            if (currentRobot == null) throw new NoSuchElementException();
            mainFrame.onAgentChange();
            refreshAgentInfo();
            robotEnterSpeedMagnitude = currentRobot.getSpeed().getMagnitude();
        } else {
            currentSocket = null;
            currentRobot = null;
        }


        gIncreaseSpeedBtn.setEnabled(true);
        gDecreaseSpeedBtn.setEnabled(true);
    }

    Agent getCurrentAgent() {
        if (currentRobot == null) {
            searchForActiveSocket();
            if (currentRobot == null)
                return null;
        }
        return currentRobot;
    }

    private void refreshAgentInfo() {
        try {
            gSpeedLbl.setText("" + currentRobot.getSpeed().getMagnitude());
            gPlaceStickyBtn.setText("" + currentRobot.getStickyCount());
            gPlaceOilBtn.setText("" + currentRobot.getOilCount());
            gPlayerTimeLeftLbl.setText("" + mainFrame.getGame().getAgentHandle(currentRobot).getTimeRemaining() / 1000);
            gPlayerLapsLbl.setText("" + currentRobot.getLap());
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void onTick(long deltaTime) {
        if (currentRobot == null || mainFrame == null)
            return;

        refreshAgentInfo();
    }

    @Override
    public void socketOpened(GameControllerSocket sender) {
        changeCurrentSocket(sender);
    }

    @Override
    public void socketClosed(GameControllerSocket sender) {
        try {
            changeCurrentSocket(sender);
        } catch (NoSuchElementException ex) {
            Heartbeat.unsubscribe(this);
            setEnabled(false);
            currentRobot = null;
            currentSocket = null;
        }
    }

    @Override
    public void addControllerSocket(GameControllerSocket socket) {
        if (socket != null) {
            sockets.add(socket);
            socket.enableStateNotification(this);
        } else {
            throw new NullPointerException();
        }
    }

    private boolean useCommand(AgentCommand command) {
        if (currentSocket == null) {
            searchForActiveSocket();
            if (currentSocket == null)
                return false;
        }

        if (sendCommandTo(command, currentSocket)) {
            Logger.log(command.getResult());
            return true;
        }

        return false;
    }

    private boolean useCommandAndChangeAgent(AgentCommand command) {
        if (currentSocket == null) {
            searchForActiveSocket();
            if (currentSocket == null)
                return false;
        }

        if (sendCommandTo(command, currentSocket)) {
            Logger.log(command.getResult());
            currentSocket.sendEndTurn();
            mainFrame.onAgentChange();
            return true;
        }

        return false;
    }

    private boolean sendCommandTo(AgentCommand command, GameControllerSocket socket) {
        return socket.sendAgentCommand(command);
    }

    private void searchForActiveSocket() {
        for (GameControllerSocket socket : sockets) {
            if (socket.isOpen()) {
                changeCurrentSocket(socket);
                return;
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        gZoomInBtn.setEnabled(enabled);
        gZoomOutBtn.setEnabled(enabled);
        gDirectionUpBtn.setEnabled(enabled);
        gDirectionDownBtn.setEnabled(enabled);
        gDirectionRightBtn.setEnabled(enabled);
        gDirectionLeftBtn.setEnabled(enabled);
        gJumpBtn.setEnabled(enabled);
        gIncreaseSpeedBtn.setEnabled(enabled);
        gDecreaseSpeedBtn.setEnabled(enabled);
        gPlaceOilBtn.setEnabled(enabled);
        gPlaceStickyBtn.setEnabled(enabled);
    }

}
