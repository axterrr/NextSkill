package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionAnswerView {
    private Long id;
    private UUID testAttemptId;
    private Long questionId;
    private Long questionOptionId;
}
