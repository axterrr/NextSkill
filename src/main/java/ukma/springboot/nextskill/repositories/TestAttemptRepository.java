package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;

import java.util.UUID;

public interface TestAttemptRepository extends JpaRepository<TestAttemptEntity, UUID> {
}
