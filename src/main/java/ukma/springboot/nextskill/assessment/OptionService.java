package ukma.springboot.nextskill.assessment;

import ukma.springboot.nextskill.common.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.common.models.views.QuestionOptionView;

import java.util.List;
import java.util.UUID;

public interface OptionService {
    List<QuestionOptionResponse> getAll();
    QuestionOptionResponse get(UUID id);
    QuestionOptionResponse create(QuestionOptionView view);
    QuestionOptionResponse update(QuestionOptionView view);
    void delete(UUID id);

    void setNewCorrect(UUID id, UUID optionId);
}
