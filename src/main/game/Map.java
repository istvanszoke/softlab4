package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import field.Field;
import field.FinishLineFieldCell;

/**
 * A Pályályát megtestesítő osztály
 * Gyakorlatilag a játék Field-jeit tárolja és kezelhetővé teszi
 */
public class Map implements Iterable<Field> {

    /**
     * A Pálya mezőinek a gyűjteménye
     */
    private final ArrayList<Field> fields;

    /**
     * A Pálya célvonali mezőinek gyűjteménye
     */
    private final ArrayList<Field> finishLineFields;

    /**
     * Az osztálynak a konstruktora
     * Létrehozza a gyűjteményeket
     */
    public Map() {
        fields = new ArrayList<Field>();
        finishLineFields = new ArrayList<Field>();
    }

    /**
     * Mező lekérdezés
     * Lekérdezhető az adott mező indexe alapján
     * @param index - A lekérdezett mező indexe
     * @return - A lekérdezet mező referenciája
     */
    public Field get(int index) {
        return fields.get(index);
    }

    /**
     * Mező hozzáadása
     * Hozzáadhatunk egy új mezőt a pályához
     * @param field - A hozzáadásra szánt mező referenciája
     */
    public void add(Field field) {
        fields.add(field);
    }

    /**
     * Célvonali mező hozzáadása
     * Hozzáadhatunk egy új célvonalhoz tartozó mezőt a pályához
     * @param finishLineField - Az új célvonali mező referenciája
     */
    public void add(FinishLineFieldCell finishLineField) {
        fields.add(finishLineField);
        finishLineFields.add(finishLineField);
    }

    /**
     * Meglévő mező törlése referenciája alapján
     * Töröhetünk a pályáról egy kiválasztott mezőt amennyiben az a pályának a része
     * @param field - A törlendő mezőnek a referenciája
     * @return
     */
    public boolean remove(Field field) {
        if (finishLineFields.contains(field)) {
            finishLineFields.remove(field);
        }

        return fields.remove(field);
    }

    /**
     * Mező indexének lekérdezése referenciája alapján
     * Lekérdezhetjük egy referencia alapján egy mezőnek az indexét
     * @param field - A keresett mezőnek a referenciája
     * @return - A mezőnek a keresett indexe
     */
    public int indexOf(Field field) {
        return fields.indexOf(field);
    }

    /**
     * Pályán van-e már elhelyezett mező
     * @return Logikai válasz a "Van-e már lehelyezett mező?" kérdésre
     */
    public boolean isEmpty() {
        return fields.isEmpty();
    }

    /**
     * Pályán lévő mezők törlése
     * Törölhetjük az eddig lehelyezett összes mezőt a pályáról
     */
    public void clear() {
        fields.clear();
        finishLineFields.clear();
    }

    /**
     * Játékosok számának megfelelő célvonali mező lekérdezése
     * Egy megfelelő számú mező kérhetünk le melyekre a játékosokat kezdetben elhelyezzük
     * @param numberOfPlayers - A játékosok száma melyeknek célvonali mező kell
     * @return - A célvonali celláknak egy gyűjteménye
     */
    public Collection<Field> findStartingPositions(int numberOfPlayers) {
        if (finishLineFields.size() < numberOfPlayers) {
            throw new IllegalArgumentException();
        }

        ArrayList<Field> suitableFields = new ArrayList<Field>();
        for (Field field : finishLineFields) {
            if (field.isEmpty()) {
                suitableFields.add(field);
            }
        }

        return suitableFields;
    }

    /**
     * Össes célvonali cella lekérdezése
     * @return - Az összes célvonali cella gyűjteménye
     */
    public Collection<Field> getFinishLineFields() {
        return finishLineFields;
    }

    /**
     * Mező iterátor lekérése
     * Lekérhetünk egy iterátort mely a pályán lévő mezők enumerálására alkalmas
     * @return - Pályán lévő mezőknek az iterátora
     */
    @Override
    public Iterator<Field> iterator() {
        return fields.iterator();
    }
}
