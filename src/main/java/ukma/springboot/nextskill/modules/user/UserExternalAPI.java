package ukma.springboot.nextskill.modules.user;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

import java.util.List;
import java.util.UUID;

public interface UserExternalAPI {

    UserEntity get(UUID id);
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
