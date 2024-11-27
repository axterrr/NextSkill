package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswerEntity, Long> {
}
