package agents;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import field.Direction;

/**
 * A sebesség vektort reprezentáló osztály.
 * Egy irányt (Direction) és egy nagyságot tárol.
 */
public class Speed implements Cloneable {
    private static final Map<Direction, Direction> parallelMapping = Collections.unmodifiableMap(new HashMap<Direction, Direction>() {{
        put(Direction.UP, Direction.DOWN);
        put(Direction.DOWN, Direction.UP);
        put(Direction.LEFT, Direction.RIGHT);
        put(Direction.RIGHT, Direction.LEFT);
    }});

    private Direction direction;
    /**
     * A sebességvektor hossza.
     */
    private int magnitude;

    /**
     * Konstruktor
     */
    public Speed(Direction direction, int magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public static Speed add(Speed lhs, Speed rhs) {
        Direction dir = lhs.magnitude > rhs.magnitude ? lhs.direction : rhs.direction;
        int mag;
        if (lhs.direction == rhs.direction) {
            mag = lhs.magnitude + rhs.magnitude;
        } else if (parallelMapping.get(lhs.direction) == rhs.direction) {
            mag = Math.max(lhs.magnitude, rhs.magnitude) - Math.min(lhs.magnitude, rhs.magnitude);
        } else {
            mag = (int) Math.sqrt((double) (lhs.magnitude * lhs.magnitude + rhs.magnitude * rhs.magnitude));
        }
        return new Speed(dir, mag);
    }

    /**
     * direction getter függvénye.
     *
     * @return - Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * direction setter függvénye.
     *
     * @param direction - A beállításra kerülő direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * magnitude getter függvénye.
     *
     * @return - magnitude
     */
    public int getMagnitude() {
        return magnitude;
    }

    /**
     * magnitude setter függvénye.
     *
     * @param magnitude - A beállításra kerülő magnitude
     */
    public void setMagnitude(int magnitude) {
        this.magnitude = Math.max(0, magnitude);
    }

    /**
     * Speed klónozását elvégző függvénye.
     *
     * @return - A klónozott objektum.
     */
    @Override
    public Speed clone() {
        try {
            Speed ret = (Speed) super.clone();
            ret.direction = direction;
            ret.magnitude = magnitude;
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Speed has to be cloneable");
        }
    }

    /**
     * Kiírást segítő toString felülírása.
     *
     * @return - debuggolásra alkalmas string.
     */
    @Override
    public String toString() {
        return "Speed: " + direction.toString() + " " + magnitude;
    }
}
