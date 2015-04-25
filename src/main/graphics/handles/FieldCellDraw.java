package graphics.handles;


import java.awt.image.BufferedImage;

import field.EmptyFieldCell;
import field.FieldCell;
import graphics.DrawHandle;

public class FieldCellDraw implements DrawHandle {

    private FieldCell cell;

    public FieldCellDraw(FieldCell cell) {
        if (cell != null)
            this.cell = cell;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public FieldCell getFieldCell() {
        return cell;
    }

}
