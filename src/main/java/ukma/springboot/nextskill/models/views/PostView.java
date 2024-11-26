package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class PostView {
    private UUID uuid;
    private String name;
    private String content;
    private Boolean isHidden;
    private UUID sectionId;
}
