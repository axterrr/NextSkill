package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionOptionResponse {
    private Long id;
    private String optionText;
    private boolean isCorrect;
}
