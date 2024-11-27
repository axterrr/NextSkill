package ukma.springboot.nextskill.models.views;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionOptionView {
    private Long id;
    private String optionText;
    private boolean isCorrect;
    private Long questionId;
}
