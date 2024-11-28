package ukma.springboot.nextskill.models.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerView {
    private UUID id;
    private UUID testAttemptId;
    private UUID questionId;
    private UUID questionOptionId;
}
