package ukma.springboot.nextskill.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.core.models.entities.QuestionOptionEntity;

import java.util.List;
import java.util.UUID;

public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, UUID> {
    List<QuestionOptionEntity> getQuestionOptionEntitiesByQuestionId(UUID questionId);
}
