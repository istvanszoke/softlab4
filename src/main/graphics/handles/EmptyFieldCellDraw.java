package graphics.handles;


import java.awt.image.BufferedImage;

import field.EmptyFieldCell;
import graphics.DrawHandle;

public class EmptyFieldCellDraw implements DrawHandle {

    private EmptyFieldCell cell;

    public EmptyFieldCellDraw(EmptyFieldCell cell) {
        if (cell != null)
            this.cell = cell;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public EmptyFieldCell getEmptyFieldCell() {
        return cell;
    }

}
