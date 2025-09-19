package ukma.springboot.nextskill.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.core.models.entities.TestEntity;

import java.util.Optional;
import java.util.UUID;

public interface TestRepository extends JpaRepository<TestEntity, UUID> {
    Optional<TestEntity> findTestEntityByAttemptsUuid(UUID attemptUuid);
    Optional<TestEntity> findByQuestionsId(UUID questionsId);
}
