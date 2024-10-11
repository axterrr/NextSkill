package ukma.springboot.nextskill.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ukma.springboot.nextskill.validation.annotations.VerifiedEmail;

public class VerifiedEmailValidator implements ConstraintValidator<VerifiedEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            return false;

        String apiKey = Dotenv.load().get("HUNTER_API_KEY");
        String resourceUrl = "https://api.hunter.io/v2/email-verifier?email=" + value + "&api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            return false;
        }
        String status = root.get("data").get("status").toString();
        return status.equals("\"valid\"");
    }
}
