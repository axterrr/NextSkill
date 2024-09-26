package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    User findByUsername(String username);
    List<User> findByName(String username);
    List<User> findBySurname(String username);
    User findByEmail(String email);
    User findByPhone(String phone);
}
