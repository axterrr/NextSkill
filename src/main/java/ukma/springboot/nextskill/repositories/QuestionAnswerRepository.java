package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;

import java.util.List;
import java.util.UUID;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswerEntity, UUID> {
    List<QuestionAnswerEntity> findByTestAttemptUuid(UUID testAttemptUuid);
}
