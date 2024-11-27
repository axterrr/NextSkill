package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionAnswerView {
    private UUID id;
    private UUID testAttemptId;
    private UUID questionId;
    private UUID questionOptionId;
}
