package ukma.springboot.nextskill.assessment.service;

import ukma.springboot.nextskill.models.responses.QuestionResponse;
import ukma.springboot.nextskill.models.views.QuestionView;

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
