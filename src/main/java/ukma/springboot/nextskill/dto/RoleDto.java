package ukma.springboot.nextskill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto {
    @NotNull
    private int id;
    @NotBlank
    private String title;
}
