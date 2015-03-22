package buff;

import agents.Robot;
import agents.Speed;

/**
 * Ragacsot reprezentáló osztály.
 * Képes változtatni a Robot illetve a felülírt commandok állapotán
 */
public class Sticky extends Buff {
    /**
     * Robot módosítása.
     * @param element - Visitelt elem.
     */    
    @Override
    public void visit(Robot element) {
        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        System.out.println("Sticky changed speed, new speed is: " + newSpeed);
    }
}
