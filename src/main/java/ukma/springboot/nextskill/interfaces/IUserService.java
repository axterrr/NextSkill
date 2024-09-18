package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.security.UserRole;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUser(UUID id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole role);

    boolean disableUser(UUID id);
    boolean enableUser(UUID id);

    boolean updateUser(UUID id, User updatedUser);
    boolean changeUserRole(UUID id, UserRole role);

    boolean deleteUser(UUID id);
}
