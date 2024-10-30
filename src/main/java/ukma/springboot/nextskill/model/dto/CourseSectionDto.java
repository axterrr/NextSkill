package ukma.springboot.nextskill.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseSectionDto {

    private UUID uuid;

    @NotBlank(message = "Course section name cannot be blank")
    private String name;

    @NotBlank(message = "Course section description cannot be blank")
    private String description;

    @NotNull(message = "Course section must belong to course")
    private CourseDto course;

    public CourseSectionDto() {
        this.uuid = UUID.randomUUID();
    }
}
