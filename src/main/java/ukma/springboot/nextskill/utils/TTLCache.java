package ukma.springboot.nextskill.utils;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TTLCache implements Cache {
    private final String name;
    private final Map<Object, CacheEntry> store = new ConcurrentHashMap<>();
    private final long timeToLive;

    public TTLCache(String name, long timeToLive, long refreshRate) {
        this.name = name;
        this.timeToLive = timeToLive;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                this::cleanUp,
                refreshRate,
                refreshRate,
                TimeUnit.MILLISECONDS
        );
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
        CacheEntry entry = store.get(key);
        if (entry == null || isExpired(entry)) {
            store.remove(key);
            return null;
        }
        return entry::getValue;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        if (wrapper == null) {
            return null;
        }
        return type.cast(wrapper.get());
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            ValueWrapper wrapper = get(key);
            if (wrapper != null) {
                return (T) wrapper.get();
            }
            T value = valueLoader.call();
            put(key, value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException("Value loader threw an exception", e);
        }
    }


    @Override
    public void put(Object key, Object value) {
        store.put(key, new CacheEntry(value, System.currentTimeMillis()));
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

    private boolean isExpired(CacheEntry entry) {
        return System.currentTimeMillis() - entry.getTimestamp() > timeToLive;
    }

    private void cleanUp() {
        store.entrySet().removeIf(entry -> isExpired(entry.getValue()));
    }

    @Getter
    private static class CacheEntry {
        private final Object value;
        private final long timestamp;

        public CacheEntry(Object value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}
