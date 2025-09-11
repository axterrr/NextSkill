package ukma.springboot.nextskill.answer;

import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.models.views.QuestionAnswerView;

import java.util.Map;
import java.util.UUID;

public interface AnswerExternalAPI {
    QuestionAnswerResponse create(QuestionAnswerView view);
    QuestionAnswerResponse update(QuestionAnswerView view);
    void delete(UUID id);

    void updateSavedAnswers(Map<String, String> map, UUID attemptId);
}
