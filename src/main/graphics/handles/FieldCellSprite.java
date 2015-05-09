package graphics.handles;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import field.FieldCell;
import graphics.SpriteHandle;

public class FieldCellSprite implements SpriteHandle {

    private FieldCell cell;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.cyan);
        g.setStroke(new BasicStroke(4));
        g.drawRect(2,2,47,47);
    }

    public FieldCellSprite(FieldCell cell) {
        if (cell != null) {
            this.cell = cell;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

}
