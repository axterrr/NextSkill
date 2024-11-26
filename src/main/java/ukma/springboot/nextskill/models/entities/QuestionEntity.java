package ukma.springboot.nextskill.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionOptionEntity> answerOptions;
}
