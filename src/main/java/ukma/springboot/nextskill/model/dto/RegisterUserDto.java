package ukma.springboot.nextskill.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ukma.springboot.nextskill.validation.annotations.VerifiedEmail;

@Data
public class RegisterUserDto {
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
    @VerifiedEmail(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password length should be more than 8")
    private String password;
}
