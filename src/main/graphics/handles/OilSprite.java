package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import buff.Oil;
import graphics.SpriteHandle;

public class OilSprite implements SpriteHandle {

    private Oil oil;
    private static BufferedImage image;
    static {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        //TODO draw one of these, or we can load it even from a file
    }

    public OilSprite(Oil oil) {
        if (oil != null)
            this.oil = oil;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemImage()
    {
        return image;
    }

    public Oil getOil() {
        return oil;
    }
}
