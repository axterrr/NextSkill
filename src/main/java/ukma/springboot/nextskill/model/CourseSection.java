package ukma.springboot.nextskill.model;

import lombok.Data;
import java.util.UUID;

@Data
public class CourseSection {
    private UUID uuid;
    private String name;
    private String description;
    private Course course;

    public CourseSection() {
        this.uuid = UUID.randomUUID();
    }

}
