package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import field.Direction;
import field.EmptyFieldCell;
import field.Field;
import field.FinishLineFieldCell;

public class Map implements Iterable<Field>, Serializable {

    private static final long serialVersionUID = -6752689712870327480L;

    private final int width;
    private final int height;
    private final List<Field> fields;
    private final List<Field> finishLineFields;

    public Map(int width, int height,
               List<Field> fields, List<Field> finishLineFields) {
        this.width = width;
        this.height = height;
        this.fields = fields;
        this.finishLineFields = finishLineFields;
        setNeigbours();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field get(int index) {
        return fields.get(index);
    }

    public void add(Field field) {
        fields.add(field);
    }

    public void add(FinishLineFieldCell finishLineField) {
        fields.add(finishLineField);
        finishLineFields.add(finishLineField);
    }

    public boolean remove(Field field) {
        if (finishLineFields.contains(field)) {
            finishLineFields.remove(field);
        }

        return fields.remove(field);
    }

    public int indexOf(Field field) {
        return fields.indexOf(field);
    }

    public boolean isEmpty() {
        return fields.isEmpty();
    }

    public void clear() {
        fields.clear();
        finishLineFields.clear();
    }

    public List<Field> findStartingPositions(int numberOfPlayers) {
        if (finishLineFields.size() < numberOfPlayers) {
            throw new IllegalArgumentException();
        }

        List<Field> suitableFields = new ArrayList<Field>();
        for (Field field : finishLineFields) {
            if (field.isEmpty()) {
                suitableFields.add(field);
            }
        }

        return suitableFields;
    }

    public Field[][] getRegion(Field center, int width, int height) {
        Field[][] fields = new Field[width][height];

        //TODO, the Map has to now how wide the mep is to deduce this information

        return fields;
    }

    public List<Field> getFinishLineFields() {
        return finishLineFields;
    }

    @Override
    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    private void setNeigbours() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Field field = get(i * width + j);

                if (i > 0) {
                    field.addNeighbour(Direction.UP, get((i - 1) * width + j));
                }

                if (i < height - 1) {
                    field.addNeighbour(Direction.DOWN, get((i + 1) * width + j));
                }

                if (i == 0) {
                    field.addNeighbour(Direction.UP, new EmptyFieldCell(-1));
                }
                if (i == height - 1) {
                    field.addNeighbour(Direction.DOWN, new EmptyFieldCell(-1));
                }

                if (j > 0) {
                    field.addNeighbour(Direction.LEFT, get(i * width + j - 1));
                }
                if (j < width - 1) {
                    field.addNeighbour(Direction.RIGHT, get(i * width + j + 1));
                }

                if (j == 0) {
                    field.addNeighbour(Direction.LEFT, new EmptyFieldCell(-1));
                }
                if (j == width - 1) {
                    field.addNeighbour(Direction.RIGHT, new EmptyFieldCell(-1));
                }
            }
        }
    }
}
