package ukma.springboot.nextskill.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_attempts")
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column
    private LocalDateTime endTime;

    @Column
    private boolean submitted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity completedBy;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @OneToMany(mappedBy = "testAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswerEntity> answers;
}
