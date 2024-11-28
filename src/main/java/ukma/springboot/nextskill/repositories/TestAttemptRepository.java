package ukma.springboot.nextskill.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestAttemptRepository extends JpaRepository<TestAttemptEntity, UUID> {
    Optional<TestAttemptEntity> findTestAttemptEntityByCompletedByUuidAndSubmittedFalseAndTest_Uuid(UUID completedBy, UUID test);
    List<TestAttemptEntity> findTestAttemptEntitiesByCompletedByUuidAndSubmittedTrueAndTest_Uuid(UUID completedBy, UUID test);

    @Transactional
    void deleteAllByTestUuid(UUID testUuid);
}
