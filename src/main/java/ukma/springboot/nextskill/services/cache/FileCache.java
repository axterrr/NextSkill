package ukma.springboot.nextskill.services.cache;

import org.springframework.cache.Cache;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;


public class FileCache implements Cache {

    private final String name;
    private final Map<Object, Object> store = new ConcurrentHashMap<>();

    public FileCache(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public ValueWrapper get(Object key) {
        return () -> store.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = store.get(key);
        if (value == null) {
            return null;
        }

        if (!type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of the required type: " + type.getName());
        }

        return type.cast(value);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            Object value = store.get(key);
            if (value != null) {
                return (T) value;
            }

            T newValue = valueLoader.call();
            store.put(key, newValue);
            return newValue;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load value for key " + key, e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        store.put(key, value);
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

}
