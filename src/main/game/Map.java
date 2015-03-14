package game;

import field.Field;

import java.util.ArrayList;

public class Map {
    private ArrayList<Field> fields;

    public Map(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
