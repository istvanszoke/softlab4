package buff;

import agents.Robot;
import agents.Speed;

/**
 * Ragacsot reprezentáló osztály.
 * Képes változtatni a Robot illetve a felülírt commandok állapotán
 */
public class Sticky extends Buff {
    int usesRemaining;

    public Sticky() {
        usesRemaining = 4;
    }

    /**
     * Robot módosítása.
     *
     * @param element - Visitelt elem.
     */
    @Override
    public void visit(Robot element) {
        if (usesRemaining == 0) {
            for (BuffListener listener : listeners) {
                listener.onRemove(this);
            }
            return;
        }

        Speed newSpeed = element.getSpeed();
        newSpeed.setMagnitude(newSpeed.getMagnitude() / 2);
        element.setSpeed(newSpeed);
        System.out.println("Sticky changed speed, new speed is: " + newSpeed);
        usesRemaining -= 1;
    }
}
