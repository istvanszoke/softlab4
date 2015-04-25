package graphics.handles;


import java.awt.image.BufferedImage;

import agents.Vacuum;
import buff.Oil;
import graphics.DrawHandle;

public class OilDraw implements DrawHandle {

    private Oil oil;

    public OilDraw(Oil oil) {
        if (oil != null)
            this.oil = oil;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public Oil getOil() {
        return oil;
    }
}
