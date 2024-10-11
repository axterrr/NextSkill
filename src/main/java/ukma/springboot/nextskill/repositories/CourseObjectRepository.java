package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.CourseObjectEntity;

import java.util.UUID;

public interface CourseObjectRepository extends JpaRepository<CourseObjectEntity, UUID> {



}
