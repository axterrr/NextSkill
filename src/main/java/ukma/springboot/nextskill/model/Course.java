package ukma.springboot.nextskill.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Course {
    private UUID uuid;
    private String name;
    private String description;
    private LocalDate creationDate;
    private User teacher;

    public Course() {
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
    }
}

