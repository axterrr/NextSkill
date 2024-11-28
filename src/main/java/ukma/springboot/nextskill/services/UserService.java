package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<UserView, UserResponse> {
    UserEntity getUserByUsername(String username);
    UserResponse getAuthenticatedUser();
    List<CourseResponse> getCourses(UUID studentId);
    List<CourseResponse> getOwnCourses(UUID teacherId);
}
