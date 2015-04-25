package graphics;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import field.Field;

public class GameGraphics extends JPanel implements ImageObserver {
    public static BufferedImage deepCopyBufferedImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = image.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private game.Map mainMap;


    private BufferedImage bufferedImage;
    private Map<Field, FieldElementSprite> drawableFields;


    private void setUp() {
        Iterator<Field> fieldIt = mainMap.iterator();
        while (fieldIt.hasNext()) {
            Field next = fieldIt.next();
            drawableFields.put(next, new FieldElementSprite(next));
        }
    }

    public GameGraphics(game.Map map) {
        mainMap = map;
        setUp();
    }

    public GameGraphics() {
        mainMap = null;
    }

    public boolean attachToMap(game.Map map) {
        if (mainMap == null) {
            mainMap = map;
            setUp();
            return true;
        } else {
            return false;
        }
    }

    public void centerFieldTo(Field center, int radius) {
        BufferedImage workingImage = new BufferedImage(50 * radius, 50 * radius, ColorModel.TRANSLUCENT);
        //TODO here is where we iterate on given fields

        int size = 2 * radius + 1;
        int fieldsToDisplay = size * size;
        int origo = mainMap.indexOf(center);

        //TODO THIS ALGORITHM IS INCORRECT. JUST FOR SEQENCE GENERATION, AND OVERVIEW:
        for (int i = origo - fieldsToDisplay / 2; i < origo + fieldsToDisplay / 2; ++i) {
            FieldElementSprite fieldSprite = drawableFields.get(mainMap.get(i));
            workingImage.getGraphics().drawImage(fieldSprite.getItemImage(), (i / size) * 50, (i % size) * 50, this);
        }


        bufferedImage = workingImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mainMap == null) {
            return;
        }

        g.drawImage(bufferedImage, 0, 0, this);
    }

    public Map<Field, FieldElementSprite> getDrawableFields() {
        return Collections.unmodifiableMap(drawableFields);
    }
}
