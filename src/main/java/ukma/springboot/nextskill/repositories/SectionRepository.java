package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.springboot.nextskill.models.entities.SectionEntity;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, UUID> {

}
