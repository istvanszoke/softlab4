package agents;

import field.Direction;
import inspector.Inspector;

public class Speed implements Cloneable {
    private Direction direction;
    private int magnitude;

    public Speed(Direction direction, int magnitude) {
        Inspector.call("Speed.Speed(Direction, int)");
        this.direction = direction;
        this.magnitude = magnitude;
        Inspector.ret("Speed.Speed");
    }

    public Direction getDirection()
    {
        Inspector.call("Speed.getDirection():Direction");
        Inspector.ret("Speed.getDirection");
        return direction;
    }

    public void setDirection(Direction direction) {
        Inspector.call("Speed.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("Speed.setDirection");
    }

    public int getMagnitude() {
        Inspector.call("Speed.getMagnitude():int");
        Inspector.ret("Speed.getMagnitude");
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        Inspector.call("Speed.setMagnitude(int)");
        this.magnitude = Math.max(0, magnitude);
        Inspector.ret("Speed.setMagnitude");
    }

    @Override
    public Speed clone() {
        Inspector.call("Speed.clone():Speed");
        try {
            Speed ret = (Speed) super.clone();
            ret.direction = direction;
            ret.magnitude = magnitude;
            Inspector.ret("Speed.clone");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            Inspector.ret("Speed.clone thrown Exception");
            throw new RuntimeException("Speed has to be cloneable");
        }

    }

    @Override
    public String toString() {
        return "Speed: " + direction.toString() + " " + magnitude;
    }
}
