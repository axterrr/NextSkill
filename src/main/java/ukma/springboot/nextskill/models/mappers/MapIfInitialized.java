package ukma.springboot.nextskill.models.mappers;

import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class MapIfInitialized {

    public static <T, R> List<R> mapIfInitialized(Collection<T> collection, Function<T, R> mapper) {
        if (Hibernate.isInitialized(collection)) {
            return collection.stream().map(mapper).toList();
        }
        return new ArrayList<>();
    }
}
