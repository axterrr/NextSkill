package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswerResponse {
    private Long id;
    private QuestionResponse question;
    private QuestionOptionResponse answerOption;
}
