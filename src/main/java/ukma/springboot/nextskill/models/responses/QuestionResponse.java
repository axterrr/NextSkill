package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QuestionResponse {
    private UUID id;
    private String questionText;
    private List<QuestionOptionResponse> questionOptions;
}
