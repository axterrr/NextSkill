package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class QuestionOptionResponse {
    private UUID id;
    private String optionText;
    private boolean isCorrect;
}
