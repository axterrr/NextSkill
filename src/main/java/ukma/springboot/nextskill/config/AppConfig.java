package ukma.springboot.nextskill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ukma.springboot.nextskill.utilities.SimpleLogger;

@Configuration
public class AppConfig {

    @Bean
    public SimpleLogger simpleLogger() {
        return new SimpleLogger();
    }
}
