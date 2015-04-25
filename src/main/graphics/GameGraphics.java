package graphics;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import agents.Agent;
import buff.Buff;
import buff.BuffListener;
import field.Field;
import game.Game;
import graphics.handles.EmptyFieldCellDraw;
import graphics.handles.RobotDraw;

public class GameGraphics extends JPanel implements ImageObserver, BuffListener
{
    private Game mainGame;
    private boolean refresh;
    private BufferedImage bufferedImage;
    private Map<Field, DrawHandle> drawableFields;
    private Map<Agent, DrawHandle> drawableRobots;
    private Map<Buff, DrawHandle> drawableBuffs;

    private void setUp() {

    }

    public GameGraphics(Game game) {
        mainGame = game;
        setUp();
    }

    public GameGraphics() {
        mainGame = null;
    }

    public boolean attachToGame(Game game) {
        if (mainGame == null) {
            mainGame = game;
            setUp();
            return true;
        } else {
            return false;
        }
    }

    public void refresh() {
        refresh = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mainGame == null)
            return;
        if (!refresh) {
            g.drawImage(bufferedImage, 0, 0, this);
            return;
        }
        //TODO


        refresh = false;
    }

    public Map<Field, DrawHandle> getDrawableFields() {
        return Collections.unmodifiableMap(drawableFields);
    }

    public Map<Agent, DrawHandle> getDrawableRobots() {
        return Collections.unmodifiableMap(drawableRobots);
    }

    public Map<Buff, DrawHandle> getDrawableBuffs () {
        return Collections.unmodifiableMap(drawableBuffs);
    }

    @Override
    public void onRemove(Buff buff) {
        drawableBuffs.remove(buff);
    }
}
