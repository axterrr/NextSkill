package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.QuestionResponse;
import ukma.springboot.nextskill.models.views.QuestionView;

import java.util.List;
import java.util.UUID;

public interface QuestionService extends GenericService<QuestionView, QuestionResponse> {
    List<QuestionResponse> getTestQuestions(UUID testId);
}
