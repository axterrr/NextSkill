package ukma.springboot.nextskill.answer;

import org.springframework.modulith.NamedInterface;
import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.models.views.QuestionAnswerView;

import java.util.Map;
import java.util.UUID;

@NamedInterface
public interface AnswerExternalAPI {
    QuestionAnswerResponse create(QuestionAnswerView view);
    QuestionAnswerResponse update(QuestionAnswerView view);
    void delete(UUID id);

    void updateSavedAnswers(Map<String, String> map, UUID attemptId);
}
