package ukma.springboot.nextskill.core.services;

import ukma.springboot.nextskill.core.models.entities.UserEntity;
import ukma.springboot.nextskill.core.models.responses.CourseResponse;
import ukma.springboot.nextskill.core.models.responses.UserResponse;
import ukma.springboot.nextskill.core.models.views.UserView;

import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<UserView, UserResponse> {
    boolean isAdmin(UUID uuid);
    boolean isTeacher(UUID uuid);
    boolean isStudent(UUID uuid);
    UserEntity getUserByUsername(String username);
    UserResponse getAuthenticatedUser();
    UserResponse getWithCourses(UUID userId);
    List<CourseResponse> getCourses(UUID userId);
}
