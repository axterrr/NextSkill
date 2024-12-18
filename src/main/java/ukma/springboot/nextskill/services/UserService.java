package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

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
