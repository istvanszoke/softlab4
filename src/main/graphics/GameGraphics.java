package graphics;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.util.Collections;
import java.util.HashMap;
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

    private final Object imageLock = new Object();


    private void buildGUI() {

    }

    private void setUp() {
        for (Field field : mainMap) {
            drawableFields.put(field, new FieldElementSprite(field));
        }
    }

    public GameGraphics(game.Map map) {
        mainMap = map;
        drawableFields = new HashMap<Field, FieldElementSprite>();
        buildGUI();
        setUp();
    }

    public GameGraphics() {
        mainMap = null;
        buildGUI();
    }

    public boolean attachToMap(game.Map map) {
        if (mainMap == null) {
            if (map == null)
                return false;
            mainMap = map;
            setUp();
            return true;
        } else {
            bufferedImage = null;
            drawableFields.clear();
            mainMap = map;
            System.gc();
            if (map != null)
                setUp();
            return true;
        }
    }

    public void centerFieldTo(Field center, int radius) {
        BufferedImage workingImage = new BufferedImage(50 * radius, 50 * radius, ColorModel.TRANSLUCENT);
        //TODO here is where we iterate on given fields

        int dim = 2*radius + 1;

        Field[][] fields = mainMap.getRegion(center, dim, dim);
        for (int x = 0; x < dim; ++x) {
            for (int y = 0; y < dim; ++y) {
                FieldElementSprite toDraw = drawableFields.get(fields[x][y]);
                workingImage.getGraphics().drawImage(toDraw.getItemImage(),x*50,y*50,this);
            }
        }

        synchronized (imageLock) {
            bufferedImage = new BufferedImage(Math.round(getWidth()), Math.round(getHeight()), ColorModel.TRANSLUCENT);
            bufferedImage.getGraphics().drawImage(workingImage.getScaledInstance(Math.round(getWidth()),
                                                                                 Math.round(getHeight()),
                                                                                 Image.SCALE_DEFAULT),
                                                  0,0,this);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mainMap == null || bufferedImage == null) {
            g.setColor(Color.BLACK);
            g.fillOval(0,0,100,100);
            return;
        }
        synchronized (imageLock) {
            g.drawImage(bufferedImage, 0, 0, this);
        }
    }

    public Map<Field, FieldElementSprite> getDrawableFields() {
        return Collections.unmodifiableMap(drawableFields);
    }
}
