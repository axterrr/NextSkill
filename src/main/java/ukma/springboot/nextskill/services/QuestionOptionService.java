package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;

import java.util.UUID;

public interface QuestionOptionService extends GenericService<QuestionOptionView, QuestionOptionResponse> {
    void setNewCorrect(UUID id, UUID optionId);
}
