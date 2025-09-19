package ukma.springboot.nextskill.assessment.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.common.models.entities.TestAttemptEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttemptRepository extends JpaRepository<TestAttemptEntity, UUID> {
    Optional<TestAttemptEntity> findTestAttemptEntityByCompletedByUuidAndSubmittedFalseAndTest_Uuid(UUID completedBy, UUID test);
    List<TestAttemptEntity> findTestAttemptEntitiesByCompletedByUuidAndSubmittedTrueAndTest_Uuid(UUID completedBy, UUID test);

    @Transactional
    void deleteAllByTestUuid(UUID testUuid);
}
