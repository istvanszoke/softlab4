package test.skeleton;

import agents.Agent;
import commands.queries.*;
import field.*;
import game.Game;
import game.GameCreator;
import game.Map;
import game.Player;
import inspector.Inspector;

import java.util.Iterator;


/**
 * Robot ugrásának tesztelése
 */
public class StaticTests {

    public static Game testGenerateNewTestGame()
    {
        int roundTime = 10;

        GameCreator creator = new GameCreator().setRoundTime(roundTime)
                .addPlayer(Player.createRobot(roundTime))
                .addPlayer(Player.createRobot(roundTime));

        Map map = new Map();
    
        EmptyFieldCell[] frontEnd = new EmptyFieldCell[6];
        EmptyFieldCell[] leftSide = new EmptyFieldCell[2];
        EmptyFieldCell[] rightSide = new EmptyFieldCell[2];
        EmptyFieldCell[] backEnd = new EmptyFieldCell[6];
        FinishLineFieldCell[] finishLine = new FinishLineFieldCell[4];
        FieldCell[] field = new FieldCell[4];

        for (int i = 0; i < 6; ++i)
        {
            frontEnd[i] = new EmptyFieldCell(i == 0 || i == 5 ? 3 : 2);
            map.add(frontEnd[i]);
        }
        leftSide[0] = new EmptyFieldCell(2); map.add(leftSide[0]);
        for (int i = 0; i < 4; ++i)
        {
            field[i] = new FieldCell(1);
            map.add(field[i]);
        }
        rightSide[0] = new EmptyFieldCell(2); map.add(rightSide[0]);
        leftSide[1] = new EmptyFieldCell(1); map.add(leftSide[1]);
        for (int i = 0; i < 4; ++i)
        {
            finishLine[i] = new FinishLineFieldCell();
            map.add(finishLine[i]);
        }
        rightSide[1] = new EmptyFieldCell(1); map.add(rightSide[1]);
        for (int i = 0; i < 6; ++i)
        {
            backEnd[i] = new EmptyFieldCell(i == 0 || i == 5 ? 2 : 1);
            map.add(backEnd[i]);
        }

        for (int x = 1; x < 5; ++x)
        {
            for (int y = 1; y < 3; ++y)
            {
                Field tmp = map.get(x + y*6);
                tmp.addNeighbour(Direction.UP, map.get(x + (y-1)*6));
                tmp.addNeighbour(Direction.DOWN, map.get(x + (y+1)*6));
                tmp.addNeighbour(Direction.LEFT, map.get(x-1 + y*6));
                tmp.addNeighbour(Direction.RIGHT, map.get(x+1 +(y-1)*6));
            }
        }

        for (int y = 0; y < 4; ++y)
        {
            Field left = map.get(y*6);
            Field right = map.get(5+y*6);

            if (y != 0)
            {
                left.addNeighbour(Direction.UP, map.get((y-1)*6));
                right.addNeighbour(Direction.UP, map.get(5 + (y-1)*6));
            }
            if (y != 3)
            {
                left.addNeighbour(Direction.DOWN, map.get((y+1)*6));
                left.addNeighbour(Direction.DOWN, map.get(5+(y+1)*6));
            }
        }

        for (int x = 0; x < 6; ++x)
        {
            Field front = map.get(x);
            Field back = map.get(3*6+x);

            if (x != 0)
            {
                front.addNeighbour(Direction.LEFT, map.get(x-1));
                front.addNeighbour(Direction.LEFT, map.get(3*6+x-1));
            }
            if (x != 5)
            {
                back.addNeighbour(Direction.RIGHT, map.get(x+1));
                back.addNeighbour(Direction.RIGHT, map.get(3*6+x+1));
            }
        }

        creator.setMap(map);

        return creator.create();

    }

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

    public static boolean testRobotFallOff(Game host, Direction directionToEmptyCell)
    {
        Agent agent = host.getCurrentAgent();
        ChangeDirectionQuery changeDirectionQuery = new ChangeDirectionQuery(directionToEmptyCell);
        JumpQuery jumpQuery = new JumpQuery();
        agent.accept(changeDirectionQuery);
        agent.accept(jumpQuery);
        return true;
    }

    public static boolean testRobotOilField(Game host)
    {
        Iterator<Field> it = host.getMap().iterator();
        Inspector.setEnabled(false);
        while (it.hasNext())
        {
            Field current = it.next();
            current.placeBuff(new buff.Oil());
        }
        Inspector.setEnabled(true);
        ChangeSpeedQuery changeSpeedQuery = new ChangeSpeedQuery(1);
        Agent agent = host.getCurrentAgent();
        agent.accept(changeSpeedQuery);
        return true;
    }

    public static boolean testRobotStickyField(Game host)
    {
        Iterator<Field> it = host.getMap().iterator();
        Inspector.setEnabled(false);
        while (it.hasNext())
        {
            Field current = it.next();
            current.placeBuff(new buff.Sticky());
        }
        Inspector.setEnabled(true);
        JumpQuery jumpQuery = new JumpQuery();
        Agent agent = host.getCurrentAgent();
        agent.accept(jumpQuery);
        return true;
    }

    public static boolean testChangePlayer(Game host)
    {
        Agent beforeAgent = host.getCurrentAgent();
        JumpQuery jumpQuery = new JumpQuery();
        beforeAgent.accept(jumpQuery);
        host.onAgentChange();
        return true;
    }

}
