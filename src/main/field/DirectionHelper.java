package field;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DirectionHelper {
    private static final Map<Direction, Direction> oppositeMapping = Collections.unmodifiableMap(new HashMap<Direction, Direction>() {
        private static final long serialVersionUID = 8404668215822539457L;

        {
            put(Direction.UP, Direction.DOWN);
            put(Direction.DOWN, Direction.UP);
            put(Direction.LEFT, Direction.RIGHT);
            put(Direction.RIGHT, Direction.LEFT);
        }
    });

    public static Direction fromString(String dir) {
        dir = dir.toLowerCase();
        if (dir.equals("up")) {
            return Direction.UP;
        } else if (dir.equals("down")) {
            return Direction.DOWN;
        } else if (dir.equals("left")) {
            return Direction.LEFT;
        } else if (dir.equals("right")) {
            return Direction.RIGHT;
        } else {
            return null;
        }
    }

    public static Direction getOpposite(Direction dir) {
        return oppositeMapping.get(dir);
    }
}
