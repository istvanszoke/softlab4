package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map;

import buff.Buff;
import buff.Oil;
import buff.Sticky;
import field.*;
import game.handle.AgentHandle;
import game.handle.PlayerHandle;
import game.handle.VacuumHandle;

public class GameSerializer {
    public static void save(Game game, String fileName) {

    }

    public static Game load(String fileName) throws IOException {
        game.Map map = null;
        Map<Integer, AgentHandle> agents = null;
        Map<Integer, Collection<Buff>> buffs = null;

        BufferedReader reader = new BufferedReader(new FileReader("src/resources/maps/" + fileName));

        String line = reader.readLine();
        while (line != null) {
            String processedLine = line.trim().toLowerCase();
            if (processedLine.equals("map:")) {
                map = processMap(reader);
            } else if (processedLine.matches("agents\\(\\w+=\\d+\\):")) {
                int roundTime = Integer.parseInt(processedLine.replaceAll("[):]", "").split("=")[1]);
                agents = processAgents(reader, roundTime);
            } else if (processedLine.equals("buffs:")) {
                buffs = processBuffs(reader);
            }

            line = reader.readLine();
        }

        reader.close();

        return merge(map, agents, buffs);
    }

    private static class FieldPlaceholder {
        public Integer distanceFromStart = null;

        public FieldPlaceholder(Integer distanceFromStart) {
            this.distanceFromStart = distanceFromStart;
        }
    }

    private static game.Map processMap(BufferedReader reader) throws IOException {
        List<FieldPlaceholder> placeholders = new ArrayList<FieldPlaceholder>();

        String line = reader.readLine();
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

        updateDistances(placeholders, numberOfRegularFields, width);
        //TODO: remove debug print
        debugDistances(placeholders, width, height);

        return mapFromPlaceholders(width, height, placeholders);

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

    private static void updateDistances(List<FieldPlaceholder> fields, int numberOfRegularFields, int mapWidth) {
        Set<FieldPlaceholder> finishLineFields = new HashSet<FieldPlaceholder>();
        for (FieldPlaceholder f : fields) {
            if (f.distanceFromStart != null && f.distanceFromStart.equals(0)) {
                finishLineFields.add(f);
            }
        }

        // The two directions determine the racing direction on the map (it will be the opposite of the
        // ones written). Currently from the finish line we will always step either UP or LEFT to begin the race
        // TODO: this limits finish lines to be either horizontal or vertical. This QUIETLY BREAKS on diagonal finish lines
        Set<FieldPlaceholder> current = new HashSet<FieldPlaceholder>();
        current.addAll(neighboursInDirection(fields, finishLineFields, Direction.DOWN, mapWidth));
        current.addAll(neighboursInDirection(fields, finishLineFields, Direction.RIGHT, mapWidth));
        cleanupCurrent(current);

        int distance = 1;
        int numberOfProcessed = setDistances(current, distance);
        ++distance;

        while (numberOfProcessed < numberOfRegularFields) {
            current.addAll(allNeighbours(fields, current, mapWidth));
            cleanupCurrent(current);
            numberOfProcessed += setDistances(current, distance);
            ++distance;
        }
    }

    private static game.Map mapFromPlaceholders(int width, int height, Collection<FieldPlaceholder> placeholders) {
        List<Field> fields = new ArrayList<Field>();
        List<Field> finishLineFields = new ArrayList<Field>();
        for (FieldPlaceholder f : placeholders) {
            Integer distance = f.distanceFromStart;
            if (distance.equals(-1)) {
                fields.add(new EmptyFieldCell(-1));
            } else if (distance.equals(0)) {
                Field field = new FinishLineFieldCell();
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

    // No bounds checking, requires a boundary of empty fields on the map to work correctly
    private static List<FieldPlaceholder> neighboursInDirection(List<FieldPlaceholder> fields,
                                                                Collection<FieldPlaceholder> current,
                                                                Direction d,
                                                                int mapWidth) {
        List<FieldPlaceholder> ret = new ArrayList<FieldPlaceholder>();

        for (FieldPlaceholder field : current) {
            int index = fields.indexOf(field);

            if (d == Direction.UP) {
                ret.add(fields.get(index - mapWidth));
            } else if (d == Direction.DOWN) {
                ret.add(fields.get(index + mapWidth));
            } else if (d == Direction.LEFT) {
                ret.add(fields.get(index - 1));
            } else if (d == Direction.RIGHT) {
                ret.add(fields.get(index + 1));
            }
        }

        return ret;
    }

    // No bounds checking, requires a boundary of empty fields on the map to work correctly
    private static List<FieldPlaceholder> allNeighbours(List<FieldPlaceholder> fields,
                                                        Collection<FieldPlaceholder> current,
                                                        int mapWidth) {
        List<FieldPlaceholder> ret = new ArrayList<FieldPlaceholder>();
        for (FieldPlaceholder field : current) {
            int index = fields.indexOf(field);
            ret.add(fields.get(index - mapWidth));
            ret.add(fields.get(index + mapWidth));
            ret.add(fields.get(index - 1));
            ret.add(fields.get(index + 1));
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
