package ukma.springboot.nextskill.models.views;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SectionView {

    private UUID uuid;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Section cannot exist without course")
    private UUID courseId;
}
