package ukma.springboot.nextskill.models.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionView {
    private UUID id;
    private String optionText;
    private boolean isCorrect;
    private UUID questionId;
}
