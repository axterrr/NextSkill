package ukma.springboot.nextskill.option;

import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OptionInternalAPI {
    List<QuestionOptionEntity> getAll();
    Optional<QuestionOptionEntity> get(UUID id);
}
