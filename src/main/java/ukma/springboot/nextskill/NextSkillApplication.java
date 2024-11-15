package ukma.springboot.nextskill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NextSkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextSkillApplication.class, args);
    }

}
