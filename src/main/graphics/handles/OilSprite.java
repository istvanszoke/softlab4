package graphics.handles;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Random;

import buff.Oil;
import graphics.SpriteHandle;

public class OilSprite implements SpriteHandle {

    private Oil oil;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        Random rnd = new Random();
        rnd.setSeed(123845214545L);
        g.setColor(Color.BLACK);
        for (int count = 0; count < 10; count++) {
            int x = rnd.nextInt(50);
            int y = rnd.nextInt(50);
            int r = 1 + rnd.nextInt(4);
            g.fillOval(x,y,r,r);
        }
    }

    public OilSprite(Oil oil) {
        if (oil != null) {
            this.oil = oil;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

}
