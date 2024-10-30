package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.model.entities.PostEntity;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    List<PostEntity> findByTitle(String title);

    List<PostEntity> findAll();
}
