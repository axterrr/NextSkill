package ukma.springboot.nextskill.models.views;

import lombok.Data;
import ukma.springboot.nextskill.models.enums.UserRole;

import java.util.UUID;

@Data
public class UserView {
    private UUID uuid;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String description;
    private UserRole role;
    private Boolean isDisabled;
    private String password;
    private String confirmPassword;
}
