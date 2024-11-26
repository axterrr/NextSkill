package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SectionResponse {
    private UUID uuid;
    private String name;
    private String description;
    private CourseResponse course;
    private List<PostResponse> posts;
}
