package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
    List<UserEntity> findByName(String username);
    List<UserEntity> findBySurname(String username);
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query("SELECT u FROM UserEntity u WHERE u.createdAt >= :createdAfter")
    List<UserEntity> findAllUsersCreatedAfter(@Param("createdAfter") LocalDateTime createdAfter);
}
