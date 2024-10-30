package ukma.springboot.nextskill.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ukma.springboot.nextskill.model.enums.UserRole;
import ukma.springboot.nextskill.validation.annotations.ValidConfirmPassword;
import ukma.springboot.nextskill.validation.annotations.Password;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ValidConfirmPassword
public class UserDto {

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
    //@VerifiedEmail(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone")
    private String phone;

    private String avatarLink;

    private String description;

    private LocalDateTime createdAt;

    private boolean isDisabled;

    @NotNull(message = "Role cannot be null")
    private UserRole role;

    @NotBlank(message = "Password is required")
    @Password
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public UserDto() {
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.isDisabled = false;
    }
}
