package graphics.handles;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import field.EmptyFieldCell;
import graphics.SpriteHandle;

public class EmptyFieldCellSprite implements SpriteHandle {

    private EmptyFieldCell cell;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        g.drawRect(2,2,47,47);
    }

    public EmptyFieldCellSprite(EmptyFieldCell cell) {
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
