package graphics.handles;


import java.awt.image.BufferedImage;

import buff.Oil;
import buff.Sticky;
import graphics.DrawHandle;

public class StickyDraw implements DrawHandle {

    private Sticky sticky;

    public StickyDraw(Sticky sticky) {
        if (sticky != null)
            this.sticky = sticky;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public Sticky getSticky() {
        return sticky;
    }
}
