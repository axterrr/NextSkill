package ukma.springboot.nextskill.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
