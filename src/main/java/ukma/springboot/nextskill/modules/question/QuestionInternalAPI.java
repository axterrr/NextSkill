package ukma.springboot.nextskill.modules.question;

import ukma.springboot.nextskill.models.responses.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionInternalAPI {
    List<QuestionResponse> getAll();
}
