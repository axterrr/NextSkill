package ukma.springboot.nextskill.configs;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ukma.springboot.nextskill.services.cache.FileCacheManager;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new FileCacheManager();
    }
}
