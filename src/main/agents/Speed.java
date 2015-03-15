package agents;

import field.Direction;

public class Speed implements Cloneable {
    private Direction direction;
    private int magnitude;

    public Speed(Direction direction, int magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
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
            return null;
        }
    }

    @Override
    public String toString() {
        return "Speed: " + direction.toString() + " " + magnitude;
    }
}
