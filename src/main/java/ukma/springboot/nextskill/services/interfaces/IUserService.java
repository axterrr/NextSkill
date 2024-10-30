package ukma.springboot.nextskill.services.interfaces;

import ukma.springboot.nextskill.model.pojo.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUser(UUID id);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(UUID id, User updatedUser);
    void deleteUser(UUID id);
    void processUser(User user);
}
