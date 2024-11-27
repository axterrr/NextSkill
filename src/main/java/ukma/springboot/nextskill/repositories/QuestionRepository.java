package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
