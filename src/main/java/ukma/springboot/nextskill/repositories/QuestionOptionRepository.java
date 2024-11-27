package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;

import java.util.UUID;

public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, UUID> {
}
