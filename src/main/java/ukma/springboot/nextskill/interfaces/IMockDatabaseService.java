package ukma.springboot.nextskill.interfaces;

import java.util.List;
import java.util.Optional;

public interface IMockDatabaseService<T, I>{
    Optional<T> find(I Id);
    List<T> getAll();
    void set(I Id, T object);
    void delete(I Id);
}
