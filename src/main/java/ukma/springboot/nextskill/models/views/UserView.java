package ukma.springboot.nextskill.models.views;

import lombok.Data;
import ukma.springboot.nextskill.models.enums.UserRole;

@Data
public class UserView {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String description;
    private UserRole role;
    private String password;
    private String confirmPassword;
}
