package game;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.*;
import java.util.Map;

import agents.Agent;
import agents.AgentVisitor;
import agents.Robot;
import agents.Vacuum;
import buff.Buff;
import buff.BuffVisitor;
import buff.Oil;
import buff.Sticky;
import feedback.NoFeedbackException;
import feedback.Result;
import field.*;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;

public class GameSerializer {
    public static boolean save(Game game, int roundTime, Direction startingDirection, String fileName) {
        game.Map map = game.getMap();
        Map<String, Collection<Integer>> agents = splitAgents(game);
        Map<String, Collection<Integer>> buffs = splitBuffs(game);

        CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src/resources/maps/" + fileName), encoder));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        InstanceOfWorkaround wa = new InstanceOfWorkaround();

        pw.println("[Map(startingDirection=" + startingDirection.toString() + ")]");
        for (int i = 0; i < map.getHeight(); ++i) {
            for (int j = 0; j < map.getWidth(); ++j) {
                Field f = map.get(i, j);
                f.accept(wa);
                pw.print(wa.output);
            }
            pw.println();
        }
        pw.println();

        pw.println("[Agents(roundTime=" + roundTime + ")]");
        for (Map.Entry<String, Collection<Integer>> e : agents.entrySet()) {
            pw.print(e.getKey() + ":");
            for (Integer i : e.getValue()) {
                pw.print(" " + i);
            }
            pw.println();
        }
        pw.println();

        pw.println("[Buffs]");
        for (Map.Entry<String, Collection<Integer>> e : buffs.entrySet()) {
            pw.print(e.getKey() + ":");
            for (Integer i : e.getValue()) {
                pw.print(" " + i);
            }
            pw.println();
        }
        pw.println();

        pw.flush();
        pw.close();

        return true;
    }

    public static Game load(File file) {
        game.Map map = null;
        Map<Integer, AgentHandle> agents = null;
        Map<Integer, Collection<Buff>> buffs = null;

        BufferedReader reader = null;

        try {
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), decoder));

            String line = reader.readLine();
            while (line != null) {
                String processedLine = line.trim().toLowerCase();
                if (processedLine.matches("\\[map\\(\\w+=\\w+\\)\\]")) {
                    String dirString = processedLine.replaceAll("[)]]", "").split("=")[1];
                    Direction startingDir = DirectionHelper.fromString(dirString);
                    if (startingDir == null) {
                        return null;
                    }

                    map = processMap(reader, startingDir);
                } else if (processedLine.matches("\\[agents\\(\\w+=\\d+\\)\\]")) {
                    int roundTime = Integer.parseInt(processedLine.replaceAll("[)]]", "").split("=")[1]);
                    agents = processAgents(reader, roundTime);
                } else if (processedLine.matches("\\[buffs\\(\\w+=\\d+\\)\\]")) {
                    int oilTime = Integer.parseInt(processedLine.replaceAll("[)]]", "").split("=")[1]);
                    Oil.setGlobalTimeout(oilTime);
                    buffs = processBuffs(reader);
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return merge(map, agents, buffs);
    }

    public static game.Map loadMap(File file) {
        game.Map map = null;
        Map<Integer, Collection<Buff>> buffs = null;

        BufferedReader reader = null;

        try {
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), decoder));

            String line = reader.readLine();
            while (line != null) {
                String processedLine = line.trim().toLowerCase();
                if (processedLine.matches("\\[map\\(\\w+=\\w+\\)\\]")) {
                    String dirString = processedLine.replaceAll("[)]]", "").split("=")[1];
                    Direction startingDir = DirectionHelper.fromString(dirString);
                    if (startingDir == null) {
                        return null;
                    }

                    map = processMap(reader, startingDir);
                } else if (processedLine.equals("[buffs]")) {
                    buffs = processBuffs(reader);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return merge(map, buffs);
    }

    private static class FieldPlaceholder {
        public Integer distanceFromStart = null;

        public FieldPlaceholder(Integer distanceFromStart) {
            this.distanceFromStart = distanceFromStart;
        }
    }

    private static class InstanceOfWorkaround implements FieldVisitor, AgentVisitor, BuffVisitor {
        public String output = "INVALID";

        @Override
        public void visit(Robot element) {
            output = "Robot";
        }

        @Override
        public void visit(Vacuum element) {
            output = "Vacuum";
        }

        @Override
        public void visit(Oil element) {
            output = "Oil";
        }

        @Override
        public void visit(Sticky element) {
            output = "Sticky";
        }

        @Override
        public void visit(FieldCell element) {
            output = " ";
        }

        @Override
        public void visit(EmptyFieldCell element) {
            output = "#";
        }

        @Override
        public void visit(FinishLineFieldCell element) {
            output = "$";
        }

        @Override
        public Result getResult() throws NoFeedbackException {
            return null;
        }
    }

    private static Map<String, Collection<Integer>> splitAgents(Game game) {
        Map<String, Collection<Integer>> ret = new HashMap<String, Collection<Integer>>();
        InstanceOfWorkaround wa = new InstanceOfWorkaround();

        for (Field f : game.getMap()) {
            Agent a = f.getAgent();
            if (a != null) {
                a.accept(wa);
                if (ret.get(wa.output) == null) {
                    ret.put(wa.output, new ArrayList<Integer>());
                }

                ret.get(wa.output).add(game.getMap().indexOf(f));
            }
        }

        return ret;
    }

    private static Map<String, Collection<Integer>> splitBuffs(Game game) {
        Map<String, Collection<Integer>> ret = new HashMap<String, Collection<Integer>>();
        InstanceOfWorkaround wa = new InstanceOfWorkaround();

        for (Field f : game.getMap()) {
            for (Buff b : f.getBuffs()) {
                b.accept(wa);
                if (ret.get(wa.output) == null) {
                    ret.put(wa.output, new ArrayList<Integer>());
                }

                ret.get(wa.output).add(game.getMap().indexOf(f));
            }
        }

        return ret;
    }

    private static game.Map processMap(BufferedReader reader, Direction startingDir) throws IOException {
        List<FieldPlaceholder> placeholders = new ArrayList<FieldPlaceholder>();

        String line = reader.readLine();
        if (line == null || line.isEmpty()) {
            return null;
        }

        int width = line.length();
        int height = 0;
        int numberOfRegularFields = 0;
        while (line != null && !line.isEmpty()) {
            for (int i = 0; i < line.length(); ++i) {
                char currentChar = line.charAt(i);
                if (currentChar == '#') {
                    placeholders.add(new FieldPlaceholder(-1));
                } else if (currentChar == '$') {
                    placeholders.add(new FieldPlaceholder(0));
                } else if (currentChar == ' ') {
                    placeholders.add(new FieldPlaceholder(null));
                    ++numberOfRegularFields;
                }
            }
            line = reader.readLine();
            ++height;
        }

        updateDistances(placeholders, numberOfRegularFields, width, height, startingDir);
        //TODO: remove debug print
        //debugDistances(placeholders, width, height);

        return mapFromPlaceholders(width, height, placeholders, startingDir);

    }

    private static Map<Integer, AgentHandle> processAgents(BufferedReader reader, int roundTime) throws IOException {
        Map<Integer, AgentHandle> agents = new HashMap<Integer, AgentHandle>();

        String line = reader.readLine();
        while (line != null && !line.isEmpty()) {
            String[] split = line.trim().toLowerCase().split(":");
            for (String field : split[1].trim().split(" ")) {
                if (split[0].equals("robot")) {
                    agents.put(Integer.parseInt(field), PlayerHandle.createRobot(roundTime));
                } else if (split[0].equals("vacuum")) {
                    agents.put(Integer.parseInt(field), VacuumHandle.createVacuum());
                }
            }

            line = reader.readLine();
        }

        return agents;
    }

    private static Map<Integer, Collection<Buff>> processBuffs(BufferedReader reader) throws IOException {
        Map<Integer, Collection<Buff>> buffs = new HashMap<Integer, Collection<Buff>>();

        String line = reader.readLine();
        while (line != null && !line.isEmpty()) {
            String[] split = line.trim().toLowerCase().split(":");
            for (String field : split[1].trim().split(" ")) {
                int fieldIndex = Integer.parseInt(field);
                if (buffs.get(fieldIndex) == null) {
                    buffs.put(fieldIndex, new ArrayList<Buff>());
                }

                if (split[0].equals("oil")) {
                    buffs.get(fieldIndex).add(new Oil());
                } else if (split[0].equals("sticky")) {
                    buffs.get(fieldIndex).add(new Sticky());
                }
            }

            line = reader.readLine();
        }

        return buffs;
    }

    private static Game merge(game.Map map,
                              Map<Integer, AgentHandle> agents,
                              Map<Integer, Collection<Buff>> buffs) {
        if (map == null || agents == null || buffs == null) {
            return null;
        }

        for (Map.Entry<Integer, AgentHandle> e : agents.entrySet()) {
            map.get(e.getKey()).onEnter(e.getValue().getAgent());
        }

        for (Map.Entry<Integer, Collection<Buff>> e : buffs.entrySet()) {
            for (Buff b : e.getValue()) {
                map.get(e.getKey()).placeBuff(b);
            }
        }

        return new Game(new GameStorage(agents.values()), map);
    }

    private static game.Map merge(game.Map map, Map<Integer, Collection<Buff>> buffs) {
        if (map == null || buffs == null) {
            return null;
        }

        for (Map.Entry<Integer, Collection<Buff>> e : buffs.entrySet()) {
            for (Buff b : e.getValue()) {
                map.get(e.getKey()).placeBuff(b);
            }
        }

        return map;
    }

    private static void updateDistances(List<FieldPlaceholder> fields, int numberOfRegularFields,
                                        int mapWidth, int mapHeight, Direction startingDir) {
        Set<FieldPlaceholder> finishLineFields = new HashSet<FieldPlaceholder>();
        for (FieldPlaceholder f : fields) {
            if (f.distanceFromStart != null && f.distanceFromStart.equals(0)) {
                finishLineFields.add(f);
            }
        }

        // The two directions determine the racing direction on the map (it will be the opposite of the
        // ones written). Currently from the finish line we will always step either UP or LEFT to begin the race
        // TODO: this limits finish lines to be either horizontal or vertical. This QUIETLY BREAKS on diagonal finish lines
        Direction opposite = DirectionHelper.getOpposite(startingDir);
        if (opposite == null) {
            throw new IllegalArgumentException();
        }

        Set<FieldPlaceholder> current = new HashSet<FieldPlaceholder>();
        current.addAll(neighboursInDirection(fields, finishLineFields, opposite, mapWidth, mapHeight));
        cleanupCurrent(current);

        int distance = 1;
        int numberOfProcessed = setDistances(current, distance);
        ++distance;

        // Currently this calculates a worst-case distance from the finish line. This might not be the
        // behaviour we want, but it is by far the easiest to implement (among the possibilities that still
        // make sense)
        while (numberOfProcessed < numberOfRegularFields) {
            current.addAll(allNeighbours(fields, current, mapWidth, mapHeight));
            cleanupCurrent(current);
            numberOfProcessed += setDistances(current, distance);
            ++distance;
        }
    }

    private static game.Map mapFromPlaceholders(int width, int height, Collection<FieldPlaceholder> placeholders,
                                                Direction startingDir) {
        List<Field> fields = new ArrayList<Field>();
        List<Field> finishLineFields = new ArrayList<Field>();
        for (FieldPlaceholder f : placeholders) {
            Integer distance = f.distanceFromStart;
            if (distance.equals(-1)) {
                fields.add(new EmptyFieldCell(-1));
            } else if (distance.equals(0)) {
                Field field = new FinishLineFieldCell(startingDir);
                fields.add(field);
                finishLineFields.add(field);
            } else {
                fields.add(new FieldCell(distance));
            }
        }

        return new game.Map(width, height, fields, finishLineFields);
    }

    private static int setDistances(Collection<FieldPlaceholder> fields, int distance) {
        int numberOfSet = 0;
        for (FieldPlaceholder f : fields) {
            f.distanceFromStart = distance;
            ++numberOfSet;
        }
        return numberOfSet;
    }

    private static List<FieldPlaceholder> neighboursInDirection(List<FieldPlaceholder> fields,
                                                                Collection<FieldPlaceholder> current,
                                                                Direction d,
                                                                int mapWidth, int mapHeight) {
        int maxIndex = mapWidth * mapHeight - 1;
        List<FieldPlaceholder> ret = new ArrayList<FieldPlaceholder>();

        for (FieldPlaceholder field : current) {
            int index = fields.indexOf(field);

            if (d == Direction.UP && index >= mapWidth) {
                ret.add(fields.get(index - mapWidth));
            } else if (d == Direction.DOWN && index <= maxIndex - mapWidth) {
                ret.add(fields.get(index + mapWidth));
            } else if (d == Direction.LEFT && index > 0) {
                ret.add(fields.get(index - 1));
            } else if (d == Direction.RIGHT && index < maxIndex) {
                ret.add(fields.get(index + 1));
            }
        }

        return ret;
    }

    private static List<FieldPlaceholder> allNeighbours(List<FieldPlaceholder> fields,
                                                        Collection<FieldPlaceholder> current,
                                                        int mapWidth, int mapHeight) {
        int maxIndex = mapWidth * mapHeight - 1;
        List<FieldPlaceholder> ret = new ArrayList<FieldPlaceholder>();

        for (FieldPlaceholder field : current) {
            int index = fields.indexOf(field);
            if (index >= mapWidth) {
                ret.add(fields.get(index - mapWidth));
            }

            if (index <= maxIndex - mapWidth) {
                ret.add(fields.get(index + mapWidth));
            }

            if (index > 0) {
                ret.add(fields.get(index - 1));
            }

            if (index < maxIndex) {
                ret.add(fields.get(index + 1));
            }
        }

        return ret;
    }

    private static void cleanupCurrent(Collection<FieldPlaceholder> current) {
        Iterator<FieldPlaceholder> i = current.iterator();
        while(i.hasNext()) {
            FieldPlaceholder f = i.next();
            if (f.distanceFromStart != null) {
                i.remove();
            }
        }
    }

    private static void debugDistances(List<FieldPlaceholder> placeholders, int width, int height) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                FieldPlaceholder f = placeholders.get(i * width + j);
                if (f.distanceFromStart.equals(-1)) {
                    System.out.print("\t#\t");
                } else if (f.distanceFromStart.equals(0)) {
                    System.out.print("\t$\t");
                } else {
                    System.out.print("\t" + f.distanceFromStart + "\t");
                }
            }
            System.out.println();
        }
        System.out.flush();
    }
}
