package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.CourseSectionEntity;

import java.util.List;
import java.util.UUID;

public interface CourseSectionRepository extends JpaRepository<CourseSectionEntity, UUID> {
    List<CourseSectionEntity> findByNameContaining(String name);
    List<CourseSectionEntity> findByCourse_Uuid(UUID courseId);
}
