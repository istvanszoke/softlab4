package proto;

import java.io.FileOutputStream;
import java.util.HashMap;

import agents.Robot;
import agents.Vacuum;
import buff.Buff;
import buff.Oil;
import buff.Sticky;
import field.Field;
import game.Game;
import game.GameCreator;
import game.Heartbeat;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;

public class TestcaseGenerator {
    public static boolean generateTestCases(final int roundTime) {
        // Test01
        Game testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = -1094092084000350243L;

                    {
                    put(PlayerHandle.createRobot(10000), 0);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = 3682889641377510397L;

                    {
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, "test01.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test02
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = 6891509298292059684L;

                    {
                    put(PlayerHandle.createRobot(10000), 0);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = 6125162343457752432L;

                    {
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, "test02.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test03
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = -3704276563919281756L;

                    {
                    put(PlayerHandle.createRobot(roundTime), 24);
                    put(PlayerHandle.createRobot(roundTime), 36);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = -4900102987982341874L;

                    {
                    put(new Sticky(), 34);
                }});

        if (!writeTest(testCase, "test03.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test04
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = 4293277202439248750L;

                    {
                    put(PlayerHandle.createRobot(roundTime), 33);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = -8120027226408767670L;

                    {
                    put(new Oil(), 34);
                }});

        if (!writeTest(testCase, "test04.map")) {
            return false;
        }
        resetInstanceCounts();


        // Test05
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = 6552228675807577774L;

                    {
                    put(PlayerHandle.createRobot(roundTime), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = 8920644231877239310L;

                    {
                }});

        if (!writeTest(testCase, "test05.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test06
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = -7486302688733593858L;

                    {
                    put(PlayerHandle.createRobot(roundTime), 34);
                    put(VacuumHandle.createVacuum(), 33);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = -2871839714906164754L;

                    {
                }});

        if (!writeTest(testCase, "test06.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test07
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = 6024511841574842543L;

                    {
                    put(PlayerHandle.createRobot(10000), 0);
                    put(VacuumHandle.createVacuum(), 33);
                    put(VacuumHandle.createVacuum(), 34);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = -7148940774077296044L;

                    {
                }});

        if (!writeTest(testCase, "test07.map")) {
            return false;
        }
        resetInstanceCounts();

        // Test08
        testCase = new Game(
                new HashMap<AgentHandle, Integer>() {
                    private static final long serialVersionUID = 4581504709607493522L;

                    {
                    put(PlayerHandle.createRobot(roundTime), 32);
                    put(PlayerHandle.createRobot(roundTime), 35);
                }},

                new GameCreator().generateMap(10, 10).getMap(),

                new HashMap<Buff, Integer>() {
                    private static final long serialVersionUID = -2333321273183682870L;

                    {
                }});

        if (!writeTest(testCase, "test08.map")) {
            return false;
        }
        resetInstanceCounts();

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

    private static void resetInstanceCounts() {
        Robot.resetInstanceCount();
        Vacuum.resetInstanceCount();
        Field.resetInstanceCount();
    }
}
