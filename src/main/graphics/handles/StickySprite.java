package graphics.handles;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Random;

import buff.Sticky;
import graphics.SpriteHandle;

public class StickySprite implements SpriteHandle {
    private Sticky sticky;

    public StickySprite(Sticky sticky) {
        if (sticky != null) {
            this.sticky = sticky;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        BufferedImage image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        Random rnd = new Random();
        rnd.setSeed(545412548321L);
        g.setColor(Color.yellow);
        for (int count = 0; count < 16 * sticky.getWear(); count++) {
            int x = rnd.nextInt(50);
            int y = rnd.nextInt(50);
            int r = 1 + rnd.nextInt(8);
            g.fillOval(x,y,r,r);
        }
        return image;
    }

}
