package ukma.springboot.nextskill.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ukma.springboot.nextskill.utils.TTLCacheManager;

@Configuration
@EnableCaching
public class CachingConfig {

    @Primary
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public CacheManager ttlCacheManager() {
        TTLCacheManager ttlCacheManager = new TTLCacheManager();
        ttlCacheManager.createCache("files", 60000, 5000); //1800000   300000
        return ttlCacheManager;
    }
}
