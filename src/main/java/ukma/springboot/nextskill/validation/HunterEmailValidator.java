package ukma.springboot.nextskill.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HunterEmailValidator implements IValidator<String> {

    @Value("${HUNTER_API_KEY}")
    private String hunterApiKey;

    @Override
    public boolean isValid(String value) {
        if (!value.matches("^\\w[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            return false;

        String resourceUrl = "https://api.hunter.io/v2/email-verifier?email=" + value + "&api_key=" + hunterApiKey;
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
