package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class Functional {



    public static <T> Collection<T> filter(Collection<? extends T> collection,
                                           Predicate<? extends T> pred) {
        ArrayList<? extends T> ret = collection;
    }

}
