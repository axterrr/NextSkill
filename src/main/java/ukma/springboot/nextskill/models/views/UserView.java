package ukma.springboot.nextskill.models.views;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.validation.constraints.ConfirmPassword;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfirmPassword
public class UserView {

    private UUID uuid;

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-z0-9_]*$", message = "Username can contain only lower latin letters, digits and underscore")
    @Size(min = 5, message = "Username length should me more than 5")
    @Size(max = 20, message = "Username length should me less than 20")
    private String username;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone")
    private String phone;

    private String description;

    @NotNull(message = "User must have a role")
    private UserRole role;

    private Boolean isDisabled;

    @NotNull(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$", message = "Password must contain upper and lower letters and digits")
    @Size(min = 8, message = "Password length should be at least 8 symbols")
    private String password;

    @NotNull(message = "Please, confirm your password")
    private String confirmPassword;
}
