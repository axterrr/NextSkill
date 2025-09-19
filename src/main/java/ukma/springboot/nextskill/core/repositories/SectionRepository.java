package ukma.springboot.nextskill.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.springboot.nextskill.core.models.entities.SectionEntity;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, UUID> {

}
