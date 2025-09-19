package ukma.springboot.nextskill.core.services;

import ukma.springboot.nextskill.core.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.core.models.views.QuestionOptionView;

import java.util.UUID;

public interface QuestionOptionService extends GenericService<QuestionOptionView, QuestionOptionResponse> {
    void setNewCorrect(UUID id, UUID optionId);
}
