package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import field.Field;
import field.FinishLineFieldCell;

public class Map implements Iterable<Field>, Serializable {
    private final List<Field> fields;
    private final List<Field> finishLineFields;

    public Map() {
        fields = new ArrayList<Field>();
        finishLineFields = new ArrayList<Field>();
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

    public Field[][] getRegion(Field center, int width, int height)
    {
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
}
