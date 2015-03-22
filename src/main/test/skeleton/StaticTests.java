package test.skeleton;

import agents.Agent;
import commands.queries.*;
import game.Game;
import field.Direction;


/**
 * Robot ugrásának tesztelése
 */
public class StaticTests {
    public static boolean testRobotJump(Game host) {
        Agent agent = host.getCurrentAgent();
        JumpQuery jumpQuery = new JumpQuery();
        agent.accept(jumpQuery);
        return true;
    }

    public static boolean testRobotUseOil(Game host) {
        Agent agent = host.getCurrentAgent();
        UseOilQuery useOilQuery = new UseOilQuery();
        agent.accept(useOilQuery);
        return true;
    }

    public static boolean testRobotUseSticky(Game host) {
        Agent agent = host.getCurrentAgent();
        UseStickyQuery useStickyQuery = new UseStickyQuery();
        agent.accept(useStickyQuery);
        return true;
    }

    public static boolean testRobotChangeSpeed(Game host, int magnitudeDelta)
    {
        Agent agent = host.getCurrentAgent();
        ChangeSpeedQuery changeSpeedQuery = new ChangeSpeedQuery(magnitudeDelta);
        agent.accept(changeSpeedQuery);
        return true;
    }

    public static boolean testRobotChangeDirection(Game host, Direction direction)
    {
        Agent agent = host.getCurrentAgent();
        ChangeDirectionQuery changeDirectionQuery = new ChangeDirectionQuery(direction);
        agent.accept(changeDirectionQuery);
        return true;
    }

}
