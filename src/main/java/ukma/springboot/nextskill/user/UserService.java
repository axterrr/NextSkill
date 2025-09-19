package ukma.springboot.nextskill.user;

import ukma.springboot.nextskill.common.models.entities.UserEntity;
import ukma.springboot.nextskill.common.models.responses.CourseResponse;
import ukma.springboot.nextskill.common.models.responses.UserResponse;
import ukma.springboot.nextskill.common.models.views.UserView;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAll();
    UserResponse get(UUID id);
    UserResponse create(UserView view);
    UserResponse update(UserView view);
    void delete(UUID id);

    boolean isAdmin(UUID uuid);
    boolean isTeacher(UUID uuid);
    boolean isStudent(UUID uuid);

    UserEntity getUserByUsername(String username);
    UserResponse getAuthenticatedUser();

    UserResponse getWithCourses(UUID userId);
    List<CourseResponse> getCourses(UUID userId);

    UserResponse getResponse(UUID id);
}
