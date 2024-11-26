package ukma.springboot.nextskill.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answer_options")
public class QuestionOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String optionText;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
