package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUser(UUID id);
    List<User> getAllUsers();
    User updateUser(UUID id, User updatedUser);
    void deleteUser(UUID id);
}
