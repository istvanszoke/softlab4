package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import buff.Sticky;
import graphics.SpriteHandle;

public class StickySprite implements SpriteHandle {

    private Sticky sticky;
    private static BufferedImage image;
    static {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        //TODO draw one of these
    }

    public StickySprite(Sticky sticky) {
        if (sticky != null)
            this.sticky = sticky;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

    public Sticky getSticky() {
        return sticky;
    }
}
