package graphics.handles;


import java.awt.image.BufferedImage;

import agents.Robot;
import graphics.SpriteHandle;

public class RobotSprite implements SpriteHandle {

    private Robot robot;
    private static int startColorIndex = 0;
    private int colorIndex;

    public static boolean setStartColorIndex(int input) {
        if (input >= 0) {
            startColorIndex = input;
            return true;
        } else {
            return false;
        }
    }

    public RobotSprite(Robot robot) {
        if (robot != null) {
            this.robot = robot;
            colorIndex = startColorIndex;
            ++startColorIndex;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        //TODO Here we actually have to implement it becuse different robots may have different representations
        return null;
    }

}
