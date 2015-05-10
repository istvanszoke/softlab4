package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import field.Direction;
import field.DirectionHelper;

public class BitmapToMapGenerator
{
    private static String directionToString(Direction dir) {
        switch (dir) {
            case UP: return "UP";
            case DOWN: return "DOWN";
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            default: throw new InvalidParameterException();
        }
    }

    private static Map<Color, ObjectTypes> colorMap;
    private static Map<ObjectTypes, String> typeMap;

    static {
        colorMap = new HashMap<Color, ObjectTypes>();
        typeMap = new HashMap<ObjectTypes, String>();
        colorMap.put(new Color(0,255,255), ObjectTypes.FIELDCELL); //Cyan
        colorMap.put(new Color(0,0,0), ObjectTypes.EMPYTFIELDCELL); // Black
        colorMap.put(new Color(255,0,0), ObjectTypes.FINISHLINEFIELDCELL); //Red
        colorMap.put(new Color(255,255,255), ObjectTypes.ROBOT); //White
        colorMap.put(new Color(255, 128, 0), ObjectTypes.VACUUM); //Orange
        colorMap.put(new Color(128,128,128), ObjectTypes.OIL); //Gray
        colorMap.put(new Color(255,255,0), ObjectTypes.STICKY); //Yellow
        typeMap.put(ObjectTypes.FIELDCELL, " ");
        typeMap.put(ObjectTypes.EMPYTFIELDCELL, "#");
        typeMap.put(ObjectTypes.FINISHLINEFIELDCELL, "$");
        typeMap.put(ObjectTypes.ROBOT, "$");
        typeMap.put(ObjectTypes.VACUUM, " ");
        typeMap.put(ObjectTypes.OIL, " ");
        typeMap.put(ObjectTypes.STICKY, " ");
    }

    private static enum ObjectTypes {
        FIELDCELL,
        EMPYTFIELDCELL,
        FINISHLINEFIELDCELL,
        ROBOT,
        VACUUM,
        OIL,
        STICKY
    }

    private static String createItemLine(String itemName, List<Integer> indexes) {
        StringBuilder sb = new StringBuilder();
        sb.append(itemName.trim() + ":");

        for (Integer index : indexes) {
            sb.append(" " + index);
        }

        return sb.toString();
    }

    public static boolean generateMapToFile(BufferedImage image, File output, Direction dir, int agentRoundTime) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(output);
        } catch (IOException ex) {
            return false;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        List<Integer> robotsOnFiled = new ArrayList<Integer>();
        List<Integer> vacuumsOnFiled = new ArrayList<Integer>();
        List<Integer> stickiesOnField = new ArrayList<Integer>();
        List<Integer> oilsOnField = new ArrayList<Integer>();

        int currentCell = 0;

        pw.println(String.format("[Map(startingDirection=%s)]", directionToString(dir)));

        for (int v = 0; v < height; ++v) {
            StringBuilder sb = new StringBuilder();
            for (int h = 0; h < width; h++) {
                Color color = new Color(image.getRGB(v,h));
                sb.append(typeMap.get(colorMap.get(color)));
                switch (colorMap.get(color)) {
                    case ROBOT:
                        robotsOnFiled.add(currentCell);
                        break;
                    case VACUUM:
                        vacuumsOnFiled.add(currentCell);
                        break;
                    case OIL:
                        oilsOnField.add(currentCell);
                        break;
                    case STICKY:
                        stickiesOnField.add(currentCell);
                        break;
                    default:
                        break;
                }
                ++currentCell;
            }
            pw.println(sb.toString());
        }

        pw.println("");
        pw.println("[Buffs]");
        pw.println(createItemLine("Oil", oilsOnField));
        pw.println(createItemLine("Sticky", stickiesOnField));
        pw.println();
        pw.println(String.format("[Agents(roundTime=%i)]", agentRoundTime));
        pw.println(createItemLine("Robot", robotsOnFiled));
        pw.println(createItemLine("Vacuum", vacuumsOnFiled));
        pw.println();

        pw.flush();
        pw.close();
        return true;
    }
}
