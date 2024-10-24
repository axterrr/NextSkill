package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.QuestionEntity;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
    List<QuestionEntity> findByTextContaining(String text);

}
