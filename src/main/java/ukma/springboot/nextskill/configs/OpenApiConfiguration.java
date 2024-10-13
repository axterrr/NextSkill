package ukma.springboot.nextskill.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "NextSkill - course management system",
        version = "0.0.1",
        description = "This API allows to work with our system via http(s) requests"
))
public class OpenApiConfiguration {}