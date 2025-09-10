package ukma.springboot.nextskill.course.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private UUID uuid;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private UserResponse teacher;
    private List<UserResponse> students;
    private List<SectionResponse> sections;
}
