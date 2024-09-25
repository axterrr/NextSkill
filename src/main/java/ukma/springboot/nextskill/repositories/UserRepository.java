package ukma.springboot.nextskill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukma.springboot.nextskill.entities.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
    UserEntity findByName(String username);
    UserEntity findBySurname(String username);
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);

}
