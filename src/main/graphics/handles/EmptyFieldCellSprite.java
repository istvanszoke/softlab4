package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import field.EmptyFieldCell;
import graphics.SpriteHandle;

public class EmptyFieldCellSprite implements SpriteHandle {

    private EmptyFieldCell cell;
    private static BufferedImage image;
    static {
        image = new BufferedImage(50,50, ColorModel.TRANSLUCENT);
        //TODO draw one of these

    }

    public EmptyFieldCellSprite(EmptyFieldCell cell) {
        if (cell != null)
            this.cell = cell;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemImage() {
        return image;
    }

    public EmptyFieldCell getEmptyFieldCell() {
        return cell;
    }

}
