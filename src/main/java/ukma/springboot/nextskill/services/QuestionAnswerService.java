package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.models.views.QuestionAnswerView;

import java.util.Map;
import java.util.UUID;

public interface QuestionAnswerService extends GenericService<QuestionAnswerView, QuestionAnswerResponse> {
    void updateSavedAnswers(Map<String, String> map, UUID attemptId);
}
