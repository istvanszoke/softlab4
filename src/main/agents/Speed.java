package agents;

import field.Direction;
import inspector.Inspector;

/**
 * A sebesség vektort reprezentáló osztály.
 * Egy irányt (Direction) és egy nagyságot tárol.
 */
public class Speed implements Cloneable {
    /**
     * A sebességvektor iránya.
     */
    private Direction direction;
    /**
     * A sebességvektor hossza.
     */
    private int magnitude;

    /**
     * Konstruktor
     */
    public Speed(Direction direction, int magnitude) {
        Inspector.call("Speed.Speed(Direction, int)");
        this.direction = direction;
        this.magnitude = magnitude;
        Inspector.ret("Speed.Speed");
    }

    /**
     * direction getter függvénye.
     * @return - Direction
     */
    public Direction getDirection()
    {
        Inspector.call("Speed.getDirection():Direction");
        Inspector.ret("Speed.getDirection");
        return direction;
    }

    /**
     * direction setter függvénye.
     * @param direction - A beállításra kerülő direction
     */
    public void setDirection(Direction direction) {
        Inspector.call("Speed.setDirection(Direction)");
        this.direction = direction;
        Inspector.ret("Speed.setDirection");
    }

    /**
     * magnitude getter függvénye.
     * @return - magnitude
     */    
    public int getMagnitude() {
        Inspector.call("Speed.getMagnitude():int");
        Inspector.ret("Speed.getMagnitude");
        return magnitude;
    }

    /**
     * magnitude setter függvénye.
     * @param magnitude - A beállításra kerülő magnitude
     */    
    public void setMagnitude(int magnitude) {
        Inspector.call("Speed.setMagnitude(int)");
        this.magnitude = Math.max(0, magnitude);
        Inspector.ret("Speed.setMagnitude");
    }
    /**
     * Speed klónozását elvégző függvénye.
     * @return - A klónozott objektum.
     */
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
    
    /**
     * Kiírást segítő toString felülírása.
     * @return - debuggolásra alkalmas string.
     */
    @Override
    public String toString() {
        return "Speed: " + direction.toString() + " " + magnitude;
    }
}
