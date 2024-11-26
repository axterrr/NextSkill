package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CourseResponse {
    private UUID uuid;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private UserResponse teacher;
    private List<UserResponse> students;
    private List<SectionResponse> sections;
}
