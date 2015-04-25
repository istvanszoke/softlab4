package graphics.handles;


import java.awt.image.BufferedImage;

import field.FieldCell;
import field.FinishLineFieldCell;
import graphics.DrawHandle;

public class FinishLineFieldCellDraw implements DrawHandle {

    private FinishLineFieldCell cell;

    public FinishLineFieldCellDraw(FinishLineFieldCell cell) {
        if (cell != null)
            this.cell = cell;
        else
            throw new NullPointerException();
    }

    @Override
    public BufferedImage getItemDrawing() {
        return null;
    }

    public FinishLineFieldCell getFinishLineFieldCell() {
        return cell;
    }
}
