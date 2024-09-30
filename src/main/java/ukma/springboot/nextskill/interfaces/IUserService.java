package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User getUser(String id);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(String id, User updatedUser);
    void deleteUser(String id);
}
