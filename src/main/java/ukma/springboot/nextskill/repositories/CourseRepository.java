package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.CourseEntity;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    List<CourseEntity> findByNameContaining(String name);
    List<CourseEntity> findByTeacher_Uuid(UUID teacherId);

    }
