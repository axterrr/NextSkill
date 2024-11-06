package ukma.springboot.nextskill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CourseDto {

    private UUID uuid;

    @NotBlank(message = "Course name cannot be blank")
    private String name;

    @NotBlank(message = "Course description cannot be blank")
    private String description;

    private LocalDate creationDate;

    @NotNull(message = "Course teacher cannot be null")
    private UserDto teacher;

    public CourseDto() {
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
    }
    public CourseDto(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
    }
    public CourseDto(UUID uuid, String name, UserDto teacher ) {
        this.name = name;
        this.uuid = uuid;
        this.teacher = teacher;
        description ="Description of course";
        creationDate = LocalDate.now();
    }

}
