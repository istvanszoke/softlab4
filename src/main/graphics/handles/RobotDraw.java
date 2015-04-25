package graphics.handles;


import java.awt.image.BufferedImage;

import agents.Robot;
import field.FinishLineFieldCell;
import graphics.DrawHandle;

public class RobotDraw implements DrawHandle {

    private Robot robot;

    public RobotDraw(Robot robot) {
        if (robot != null)
            this.robot = robot;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public Robot getRobot() {
        return robot;
    }
}
