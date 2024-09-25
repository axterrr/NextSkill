package ukma.springboot.nextskill.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private final UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserEntity teacher;

    public CourseEntity() {
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }
}
