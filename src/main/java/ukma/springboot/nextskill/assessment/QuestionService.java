package ukma.springboot.nextskill.assessment;

import ukma.springboot.nextskill.common.models.responses.QuestionResponse;
import ukma.springboot.nextskill.common.models.views.QuestionView;

import java.util.List;
import java.util.UUID;


public interface QuestionService {
    List<QuestionResponse> getAll();
    QuestionResponse get(UUID id);
    QuestionResponse create(QuestionView view);
    QuestionResponse update(QuestionView view);
    void delete(UUID id);

    List<QuestionResponse> getTestQuestions(UUID testId);
    QuestionResponse getQuestionByOption(UUID optionId);
}
