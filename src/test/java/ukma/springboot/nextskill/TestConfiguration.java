package ukma.springboot.nextskill;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ukma.springboot.nextskill.validation.HunterEmailValidator;
import ukma.springboot.nextskill.validation.IValidator;

@Configuration
@Import({HunterEmailValidator.class})
public class TestConfiguration {

    @Bean
    public IValidator<String> emailValidator() {
        return new HunterEmailValidator();
    }
}
