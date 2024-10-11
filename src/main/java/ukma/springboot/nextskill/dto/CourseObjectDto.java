package ukma.springboot.nextskill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseObjectDto {

    private UUID uuid;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 255, message = "Title should be between 3 and 255 characters")
    private String title;

    @NotNull(message = "Order is mandatory")
    @PositiveOrZero(message = "Order must be zero or positive")
    private int order;

    private boolean isHidden = false;

    @Size(min = 10, max = 500, message = "Description should be between 10 and 500 characters")
    private String description;
}
