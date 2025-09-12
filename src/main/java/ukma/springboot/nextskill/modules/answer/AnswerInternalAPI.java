package ukma.springboot.nextskill.modules.answer;

import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;

import java.util.List;
import java.util.UUID;

public interface AnswerInternalAPI {
    List<QuestionAnswerResponse> getAll();
    QuestionAnswerResponse get(UUID id);
}
