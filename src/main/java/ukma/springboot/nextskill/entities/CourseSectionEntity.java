package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "questions")
@Data

public class CourseSectionEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    public CourseSectionEntity() {
        this.uuid = UUID.randomUUID();
    }

}
