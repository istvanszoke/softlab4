package graphics.handles;


import java.awt.image.BufferedImage;

import agents.Robot;
import graphics.SpriteHandle;

public class RobotSprite implements SpriteHandle {

    private Robot robot;

    public RobotSprite(Robot robot) {
        if (robot != null)
            this.robot = robot;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemImage() {
        //TODO Here we actually have to implement it becuse different robots may have different representations
        return null;
    }

    public Robot getRobot() {
        return robot;
    }
}
