package ukma.springboot.nextskill.services;

import java.util.List;
import java.util.UUID;

public interface GenericService<V,R> {
    List<R> getAll();
    R get(UUID id);
    R create(V view);
    R update(V view);
    void delete(UUID id);
}
