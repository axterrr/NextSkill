package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;

public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, Long> {
}
