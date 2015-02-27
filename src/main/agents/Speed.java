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

    public int getMagnitude() {
        return magnitude;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
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
}
