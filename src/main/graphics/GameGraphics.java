package graphics;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;

import game.Game;
import graphics.handles.EmptyFieldCellDraw;

public class GameGraphics extends JPanel implements ImageObserver
{
    private Game mainGame;
    private boolean refresh;
    private BufferedImage bufferedImage;


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
}
