package ukma.springboot.nextskill.services.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import java.util.HashMap;
import java.util.Map;

import java.util.Collection;

public class FileCacheManager implements CacheManager {

    private final Map<String, Cache> cacheMap = new HashMap<>();

    @Override
    public Cache getCache(String name) {
        return cacheMap.computeIfAbsent(name, FileCache::new);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}
