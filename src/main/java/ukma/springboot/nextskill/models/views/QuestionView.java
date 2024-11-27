package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionView {
    private UUID id;
    private String questionText;
    private UUID testId;
}
