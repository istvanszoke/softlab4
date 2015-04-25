package graphics.handles;


import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import field.FinishLineFieldCell;
import graphics.SpriteHandle;

public class FinishLineFieldCellSprite implements SpriteHandle {

    private FinishLineFieldCell cell;
    private static BufferedImage image;

    static {
        image = new BufferedImage(50, 50, ColorModel.TRANSLUCENT);
        //TODO draw one of these
    }

    public FinishLineFieldCellSprite(FinishLineFieldCell cell) {
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

    public FinishLineFieldCell getFinishLineFieldCell() {
        return cell;
    }
}
