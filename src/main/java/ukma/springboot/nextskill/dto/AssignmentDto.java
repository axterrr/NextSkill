package ukma.springboot.nextskill.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AssignmentDto extends PostDto{
    @NotNull
    private LocalDateTime dueTo;
    @NotNull
    @Min(0)
    private Integer grade;
}
