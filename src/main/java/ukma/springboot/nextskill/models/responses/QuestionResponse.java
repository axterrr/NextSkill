package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String questionText;
    private List<QuestionOptionResponse> questionOptions;
}
