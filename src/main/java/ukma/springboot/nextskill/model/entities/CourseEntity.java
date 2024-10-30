package ukma.springboot.nextskill.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Data
public class CourseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserEntity teacher;

    @ManyToMany(mappedBy = "courses")
    private List<UserEntity> users;

    public CourseEntity() {
        this.uuid = UUID.randomUUID();
        creationDate = LocalDate.now();
        users = new ArrayList<>();
    }
}
