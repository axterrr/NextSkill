package ukma.springboot.nextskill.modules.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<QuestionAnswerEntity, UUID> {
    List<QuestionAnswerEntity> findByTestAttemptUuid(UUID testAttemptUuid);
}
