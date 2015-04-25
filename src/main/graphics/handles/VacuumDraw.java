package graphics.handles;


import java.awt.image.BufferedImage;

import agents.Robot;
import agents.Vacuum;
import graphics.DrawHandle;

public class VacuumDraw implements DrawHandle {

    private Vacuum vacuum;

    public VacuumDraw(Vacuum vacuum) {
        if (vacuum != null)
            this.vacuum = vacuum;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public Vacuum getVacuum() {
        return vacuum;
    }
}
