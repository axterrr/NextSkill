package ukma.springboot.nextskill.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.common.models.entities.QuestionAnswerEntity;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<QuestionAnswerEntity, UUID> {
    List<QuestionAnswerEntity> findByTestAttemptUuid(UUID testAttemptUuid);
}
