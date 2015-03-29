package functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Collections {
    public static <T> Collection<T> filter(Collection<? extends T> collection,
                                           Predicate<? super T> filter) {
        List<T> ret = new ArrayList<T>();
        for (T element : collection) {
            if (filter.test(element)) {
                ret.add(element);
            }
        }
        return ret;
    }

    public static <T, U> Collection<U> map(Collection<? extends T> collection,
                                           Function<? super T, ? extends U> mapper) {
        List<U> ret = new ArrayList<U>();
        for (T element : collection) {
            ret.add(mapper.apply(element));
        }
        return ret;
    }

    public static <T> void removeIf(Collection<? extends T> collection,
                                    Predicate<? super T> condition) {
        for (Iterator<? extends T> i = collection.iterator(); i.hasNext(); ) {
            if (condition.test(i.next())) {
                i.remove();
            }
        }
    }
}
