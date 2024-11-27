package ukma.springboot.nextskill.models.views;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class QuestionOptionView {
    private UUID id;
    private String optionText;
    private boolean isCorrect;
    private UUID questionId;
}
