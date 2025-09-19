package ukma.springboot.nextskill.assessment;

import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;

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
