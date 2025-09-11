package ukma.springboot.nextskill.option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;

import java.util.List;
import java.util.UUID;

public interface OptionRepository extends JpaRepository<QuestionOptionEntity, UUID> {
    List<QuestionOptionEntity> getQuestionOptionEntitiesByQuestionId(UUID questionId);
}
