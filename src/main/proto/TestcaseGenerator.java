package proto;

import java.util.HashMap;

import agents.Robot;
import agents.Vacuum;
import buff.Buff;
import buff.Oil;
import buff.Sticky;
import field.Direction;
import field.Field;
import game.Game;
import game.GameCreator;
import game.GameSerializer;
import game.Heartbeat;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;

public class TestcaseGenerator {
    public static boolean generateTestCases(final int roundTime) {
        // Test01
        Game testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(10000), 11);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, 10000, "test01.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test02
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(10000), 11);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, 10000, "test02.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test03
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 24);
                    put(PlayerHandle.createRobot(roundTime), 36);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Sticky(), 34);
                }});

        if (!writeTest(testCase, roundTime, "test03.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test04
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 33);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, roundTime, "test04.map")) {
            return false;
        }
        resetInstanceCounts();


        // Test05
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, roundTime, "test05.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test06
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 34);
                    put(VacuumHandle.createVacuum(), 33);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, roundTime, "test06.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test07
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(10000), 11);
                    put(VacuumHandle.createVacuum(), 33);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, 10000, "test07.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test08
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 32);
                    put(PlayerHandle.createRobot(roundTime), 35);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, roundTime, "test08.map")) {
            return false;
        }
        resetInstanceCounts();

        Heartbeat.purgeListeners();
        return true;
    }

    private static boolean writeTest(Game testCase, int roundTime, String mapName) {
        return GameSerializer.save(testCase, roundTime, Direction.UP, mapName);
    }

    private static void resetInstanceCounts() {
        Robot.resetInstanceCount();
        Vacuum.resetInstanceCount();
        Field.resetInstanceCount();
    }
}
