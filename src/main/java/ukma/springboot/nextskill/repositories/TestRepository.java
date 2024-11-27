package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.models.entities.TestEntity;

import java.util.UUID;

public interface TestRepository extends JpaRepository<TestEntity, UUID> {
}
