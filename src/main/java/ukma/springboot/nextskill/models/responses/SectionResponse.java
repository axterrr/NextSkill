package ukma.springboot.nextskill.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {
    private UUID uuid;
    private String name;
    private String description;
    private CourseResponse course;
    private List<PostResponse> posts;
    private List<TestResponse> tests;
}
