package ukma.springboot.nextskill.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PostDto extends CourseObjectDto{
    @NotNull
    private String content;
    private List<String> attachedFiles;
}
