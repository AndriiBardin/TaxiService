package taxi.services;

import java.util.List;

public interface GenericService<T, I> {
    T create(T value);

    T update(T value);

    T get(I id);

    List<T> getAll();

    boolean delete(I id);
}
