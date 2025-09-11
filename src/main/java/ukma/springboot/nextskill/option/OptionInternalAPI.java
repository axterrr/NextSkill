package ukma.springboot.nextskill.option;

import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;

import java.util.List;
import java.util.UUID;

public interface OptionInternalAPI {
    List<QuestionOptionResponse> getAll();
    QuestionOptionResponse get(UUID id);
}
