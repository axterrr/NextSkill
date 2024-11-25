package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class PostView {
    private String name;
    private String content;
    private UUID sectionId;
}
