package ukma.springboot.nextskill.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sections")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_uuid", updatable = false)
    private CourseEntity course;

    @OneToMany(mappedBy = "section")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "section")
    private List<TestEntity> tests;
}
