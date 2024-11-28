package ukma.springboot.nextskill.models.views;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostView {

    private UUID uuid;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String content;

    private Boolean isHidden;

    @NotNull(message = "Post cannot exist without section")
    private UUID sectionId;
}
