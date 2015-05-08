package agents;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import field.Direction;
import field.DirectionHelper;

public class Speed implements Cloneable, Serializable {
    private static final long serialVersionUID = 624748655659797670L;

    private Direction direction;
    private int magnitude;

    public Speed(Direction direction, int magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public static Speed add(Speed lhs, Speed rhs) {
        Direction dir = lhs.magnitude > rhs.magnitude ? lhs.direction : rhs.direction;
        int mag;
        if (lhs.direction == rhs.direction) {
            mag = lhs.magnitude + rhs.magnitude;
        } else if (DirectionHelper.getOpposite(lhs.direction) == rhs.direction) {
            mag = Math.max(lhs.magnitude, rhs.magnitude) - Math.min(lhs.magnitude, rhs.magnitude);
        } else {
            mag = (int) Math.sqrt((double) (lhs.magnitude * lhs.magnitude + rhs.magnitude * rhs.magnitude));
        }
        return new Speed(dir, mag);
    }

    public static Speed getOpposite(Speed speed) {
        return new Speed(DirectionHelper.getOpposite(speed.getDirection()), speed.getMagnitude());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = Math.max(0, magnitude);
    }

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

    @Override
    public String toString() {
        return "Speed: " + direction.toString() + " " + magnitude;
    }
}
