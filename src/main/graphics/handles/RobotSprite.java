package graphics.handles;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.HashMap;
import java.util.Map;

import agents.Agent;
import agents.Robot;
import field.Direction;
import graphics.SpriteHandle;

import static field.Direction.*;

public class RobotSprite implements SpriteHandle {

    private Robot robot;
    private BufferedImage image;
    private static int startColorIndex = 0;
    private int colorIndex;
    private static Map<Robot, Integer> agentColorMapping;

    static {
        agentColorMapping = new HashMap<Robot, Integer>();
    }

    static private Color getColorFromIndex (int index) {
        switch (index) {
            case 0:     return Color.BLUE;
            case 1:     return Color.GREEN;
            case 2:     return Color.RED;
            case 3:     return Color.ORANGE;
            default:    return Color.BLACK;
        }
    }

    static private String getColorNameFromIndex (int index) {
        switch (index) {
            case 0:     return "Kék";
            case 1:     return "Zöld";
            case 2:     return "Piros";
            case 3:     return "Narancssárga";
            default:    return "Egyik fekete";
        }
    }

    private void generateImage() {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setColor(getColorFromIndex(colorIndex));
        g.setStroke(new BasicStroke(5));
        g.drawOval(8,8,34,34);
        switch (robot.getSpeed().getDirection()) {
            case UP:
                g.fillPolygon(new int[] {25, 37, 13}, new int[] {13, 37, 37}, 3);
                break;
            case DOWN:
                g.fillPolygon(new int[] {25, 37, 13}, new int[] {37, 13, 13}, 3);
                break;
            case LEFT:
                g.fillPolygon(new int[] {13, 37, 37}, new int[] {25, 13, 37}, 3);
                break;
            case RIGHT:
                g.fillPolygon(new int[] {37, 13, 13}, new int[] {25, 13, 37}, 3);
                break;
        }
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
            Integer storedColorIndex = agentColorMapping.get(robot);
            if (storedColorIndex == null) {
                colorIndex = startColorIndex;
                agentColorMapping.put(robot, colorIndex);
            } else {
                colorIndex = storedColorIndex;
            }
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

    public static Map<Agent, String>getAgentMapping() {
        Map<Agent, String> result = new HashMap<Agent, String>();
        for (Robot robot : agentColorMapping.keySet()) {
            result.put(robot, getColorNameFromIndex(agentColorMapping.get(robot)));
        }
        return result;
    }
}
