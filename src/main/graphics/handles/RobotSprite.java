package graphics.handles;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import agents.Robot;
import graphics.SpriteHandle;

public class RobotSprite implements SpriteHandle {

    private Robot robot;
    private BufferedImage image;
    private static int startColorIndex = 0;
    private int colorIndex;

    static private Color getColorFromIndex (int index) {
        switch (index) {
            case 0:     return Color.BLUE;
            case 1:     return Color.GREEN;
            case 2:     return Color.RED;
            case 3:     return Color.ORANGE;
            default:    return Color.BLACK;
        }
    }

    private void generateImage() {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setColor(getColorFromIndex(colorIndex));
        g.setStroke(new BasicStroke(5));
        g.drawOval(8,8,34,34);
    }

    public static boolean setStartColorIndex(int input) {
        if (input >= 0) {
            startColorIndex = input % 4;
            return true;
        } else {
            return false;
        }
    }

    public RobotSprite(Robot robot) {
        if (robot != null) {
            this.robot = robot;
            colorIndex = startColorIndex;
            generateImage();
            startColorIndex = (startColorIndex + 1) % 4;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

}
