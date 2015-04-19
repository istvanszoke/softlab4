package proto;

import java.io.FileOutputStream;
import java.util.HashMap;

import buff.Buff;
import buff.Oil;
import buff.Sticky;
import game.Game;
import game.GameCreator;
import game.Heartbeat;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;

public class TestcaseGenerator {
    public static boolean generateTestCases(final int roundTime) {
        // Test03
        Game testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 0);
                    put(PlayerHandle.createRobot(roundTime), 1);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, "test03.map")) {
            return false;
        }

        // Test05
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 24);
                    put(PlayerHandle.createRobot(roundTime), 35);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Sticky(), 34);
                }});

        if (!writeTest(testCase, "test05.map")) {
            return false;
        }

        // Test06
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 0);
                    put(PlayerHandle.createRobot(roundTime), 33);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, "test06.map")) {
            return false;
        }

        // Test07
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 0);
                    put(PlayerHandle.createRobot(roundTime), 24);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, "test07.map")) {
            return false;
        }

        // Test08
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 0);
                    put(VacuumHandle.createVacuum(), 33);
                    put(PlayerHandle.createRobot(roundTime), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, "test08.map")) {
            return false;
        }

        // Test09
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(VacuumHandle.createVacuum(), 33);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, "test09.map")) {
            return false;
        }

        // Test10
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {{
                    put(PlayerHandle.createRobot(roundTime), 32);
                    put(PlayerHandle.createRobot(roundTime), 35);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {{
                }});

        if (!writeTest(testCase, "test10.map")) {
            return false;
        }

        Heartbeat.purgeListeners();
        return true;
    }

    private static boolean writeTest(Game testCase, String mapName) {
        try {
            FileOutputStream fos = new FileOutputStream("src/resources/maps/" + mapName);
            if (GameCreator.serializeGame(testCase, fos)) {
                fos.flush();
                fos.close();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
