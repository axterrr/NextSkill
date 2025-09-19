package ukma.springboot.nextskill.core.services;

import ukma.springboot.nextskill.core.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.core.models.views.QuestionAnswerView;

import java.util.Map;
import java.util.UUID;

public interface QuestionAnswerService extends GenericService<QuestionAnswerView, QuestionAnswerResponse> {
    void updateSavedAnswers(Map<String, String> map, UUID attemptId);
}
