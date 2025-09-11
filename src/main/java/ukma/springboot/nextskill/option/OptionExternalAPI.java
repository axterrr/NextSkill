package ukma.springboot.nextskill.option;

import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;

import java.util.UUID;

public interface OptionExternalAPI {
    QuestionOptionResponse create(QuestionOptionView view);
    QuestionOptionResponse update(QuestionOptionView view);
    void delete(UUID id);

    void setNewCorrect(UUID id, UUID optionId);
}
