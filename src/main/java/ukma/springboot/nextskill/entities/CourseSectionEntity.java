package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class CourseSectionEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private final UUID uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }
}
