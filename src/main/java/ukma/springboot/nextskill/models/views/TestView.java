package ukma.springboot.nextskill.models.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestView {
    private UUID uuid;
    private String name;
    private String description;
    private boolean isHidden;
    private UUID sectionId;
}
