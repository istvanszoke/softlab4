package graphics.handles;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import agents.Vacuum;
import graphics.SpriteHandle;

public class VacuumSprite implements SpriteHandle {

    private Vacuum vacuum;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.MAGENTA);
        g.setStroke(new BasicStroke(5));
        g.drawPolygon(new int[] {25,10,40}, new int[] {10,40,40}, 3);
    }

    public VacuumSprite(Vacuum vacuum) {
        if (vacuum != null) {
            this.vacuum = vacuum;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

}
