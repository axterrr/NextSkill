package ukma.springboot.nextskill.models.views;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PostView {

    private UUID uuid;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String content;

    private Boolean isHidden;

    @NotNull(message = "Post cannot exist without section")
    private UUID sectionId;
}
