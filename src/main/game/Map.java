package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
        setNeighbours();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field get(int row, int col) {
        if (row < 0 || row >= height)
            return null;
        if (col < 0 || col >= width)
            return null;
        return fields.get(row * width + col);
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

    public Coord coordOf(Field field) {
        int index = fields.indexOf(field);
        int row = index / width;
        int col = index - (index / width) * width;
        return new Coord(row, col);
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

        Coord coord = coordOf(center);

        int hw = width/2;
        int hh = height/2;

        int wcomp = (width + 1) % 2;
        int hcomp = (height + 1) % 2;

        int ix = 0;
        int iy = 0;

        for (int x = coord.getRow()-hw+wcomp; x < coord.getRow()+hw; ++x) {
            for (int y = coord.getCol()-hh+hcomp; y < coord.getCol()+hh; ++y) {
                fields[ix][iy] = get(y,x);
                ++iy;
            }
            iy = 0;
            ++ix;
        }

        return fields;
    }

    public List<Field> getFinishLineFields() {
        return finishLineFields;
    }

    @Override
    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    public Collection<Field> getNeighbours(final Field f) {
        List<Field> ret = new ArrayList<Field>() {{
            add(f.getNeighbour(Direction.UP));
            add(f.getNeighbour(Direction.DOWN));
            add(f.getNeighbour(Direction.LEFT));
            add(f.getNeighbour(Direction.RIGHT));
        }};

        Iterator<Field> i = ret.iterator();
        while (i.hasNext()) {
            Field current = i.next();
            if (current == null) {
                i.remove();
            }
        }

        return ret;
    }

    private void setNeighbours() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Field field = get(i, j);

                if (i > 0) {
                    field.addNeighbour(Direction.UP, get(i - 1, j));
                }

                if (i < height - 1) {
                    field.addNeighbour(Direction.DOWN, get(i + 1, j));
                }

                if (i == 0) {
                    field.addNeighbour(Direction.UP, new EmptyFieldCell(-1));
                }
                if (i == height - 1) {
                    field.addNeighbour(Direction.DOWN, new EmptyFieldCell(-1));
                }

                if (j > 0) {
                    field.addNeighbour(Direction.LEFT, get(i, j - 1));
                }
                if (j < width - 1) {
                    field.addNeighbour(Direction.RIGHT, get(i, j + 1));
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
