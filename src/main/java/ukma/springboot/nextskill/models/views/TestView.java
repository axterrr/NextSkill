package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class TestView {
    private UUID uuid;
    private String name;
    private String description;
    private boolean isHidden;
    private UUID sectionId;
}
