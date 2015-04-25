package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import field.FieldCell;
import graphics.SpriteHandle;

public class FieldCellSprite implements SpriteHandle {

    private FieldCell cell;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        //TODO draw one of these
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

    public FieldCell getFieldCell() {
        return cell;
    }

}
