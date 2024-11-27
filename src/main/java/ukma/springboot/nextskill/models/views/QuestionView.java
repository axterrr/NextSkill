package ukma.springboot.nextskill.models.views;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class QuestionView {
    private UUID id;
    private String questionText;
    private UUID testId;
}
