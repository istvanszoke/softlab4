package graphics;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import agents.Agent;
import field.EmptyFieldCell;
import field.Field;
import game.GameListener;
import game.handle.AgentHandle;
import graphics.handles.EmptyFieldCellSprite;

public class GameGraphics extends JPanel implements ImageObserver, ComponentListener {
    public static BufferedImage deepCopyBufferedImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = image.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private game.Map mainMap;


    private BufferedImage bufferedImage;
    private BufferedImage lastGeneratedImage;
    private Map<Field, FieldElementSprite> drawableFields;
    private Field lastCenter;
    private int lastRadius;

    private final Object imageLock = new Object();


    private void buildGUI() {

    }

    private void setUp() {
        drawableFields = new HashMap<Field, FieldElementSprite>();
        for (Field field : mainMap) {
            drawableFields.put(field, new FieldElementSprite(field));
        }
        addComponentListener(this);
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
        int dim = 2*radius + 1;
        BufferedImage workingImage = new BufferedImage(50 * dim, 50 * dim, ColorModel.TRANSLUCENT);
        //TODO here is where we iterate on given fields
        lastCenter = center;
        lastRadius = radius;

        EmptyFieldCellSprite defaultFieldSprite = new EmptyFieldCellSprite(new EmptyFieldCell(0));

        Field[][] fields = mainMap.getRegion(center, dim, dim);
        for (int x = 0; x < dim; ++x) {
            for (int y = 0; y < dim; ++y) {
                if (fields[x][y] != null) {
                    FieldElementSprite toDraw = drawableFields.get(fields[x][y]);
                    workingImage.getGraphics().drawImage(toDraw.getItemImage(), x * 50, y * 50, this);
                    //workingImage.getGraphics().drawImage(defaultFieldSprite.getItemImage(), x*50, y*50, this);
                } else {
                    workingImage.getGraphics().drawImage(defaultFieldSprite.getItemImage(), x*50, y*50, this);
                }
            }
        }


        lastGeneratedImage = workingImage;
        synchronized (imageLock) {
            bufferedImage = new BufferedImage(Math.round(getWidth()), Math.round(getHeight()), ColorModel.TRANSLUCENT);
            bufferedImage.getGraphics().drawImage(workingImage.getScaledInstance(Math.round(getWidth()),
                                                                                 Math.round(getHeight()),
                                                                                 Image.SCALE_DEFAULT),
                                                  0,0,this);
        }
    }

    public void regenerateImage() {
        centerFieldTo(lastCenter, lastRadius);
    }

    public void redrawLastImage() {
        synchronized (imageLock) {
            bufferedImage = new BufferedImage(Math.round(getWidth()), Math.round(getHeight()), ColorModel.TRANSLUCENT);
            bufferedImage.getGraphics().drawImage(lastGeneratedImage.getScaledInstance(Math.round(getWidth()),
                                                                                       Math.round(getHeight()),
                                                                                       Image.SCALE_DEFAULT),
                                                  0,0,this);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mainMap == null || bufferedImage == null) {
            return;
        }
        synchronized (imageLock) {
            g.drawImage(bufferedImage, 0, 0, this);
        }
    }

    public Map<Field, FieldElementSprite> getDrawableFields() {
        return Collections.unmodifiableMap(drawableFields);
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        if (lastGeneratedImage != null)
            redrawLastImage();
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }
}
