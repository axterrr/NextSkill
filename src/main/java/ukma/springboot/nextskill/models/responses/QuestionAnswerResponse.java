package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class QuestionAnswerResponse {
    private UUID id;
    private QuestionResponse question;
    private QuestionOptionResponse answerOption;
}
