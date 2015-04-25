package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import agents.Vacuum;
import graphics.SpriteHandle;

public class VacuumSprite implements SpriteHandle {

    private Vacuum vacuum;
    private static BufferedImage image;
    static {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        //TODO draw one of these

    }

    public VacuumSprite(Vacuum vacuum) {
        if (vacuum != null)
            this.vacuum = vacuum;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

    public Vacuum getVacuum() {
        return vacuum;
    }
}
