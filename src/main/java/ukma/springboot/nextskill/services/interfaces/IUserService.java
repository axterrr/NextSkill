package ukma.springboot.nextskill.services.interfaces;

import ukma.springboot.nextskill.model.dto.UserDto;
import ukma.springboot.nextskill.model.pojo.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUser(UUID id);
    List<User> getAllUsers();
    User createUser(UserDto user);
    User updateUser(UUID id, UserDto updatedUser);
    void deleteUser(UUID id);
    void processUser(UserDto user);
    User getUserByEmail(String email);
}
