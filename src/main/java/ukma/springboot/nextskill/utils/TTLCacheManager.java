package ukma.springboot.nextskill.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TTLCacheManager implements CacheManager {
    private final Map<String, TTLCache> cacheMap;

    public TTLCacheManager() {
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public Cache getCache(String name) {
        return cacheMap.get(name);
    }

    public Cache createCache(String name, long ttl, long refreshRate) {
        if (cacheMap.containsKey(name)) {
            throw new IllegalArgumentException("Cache with name '" + name + "' already exists.");
        }
        TTLCache newCache = new TTLCache(name, ttl, refreshRate);
        cacheMap.put(name, newCache);
        return newCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}
