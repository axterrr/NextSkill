package ukma.springboot.nextskill.models.views;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsView {
    private String username;
    private String password;
}
