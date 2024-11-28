package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CourseRestController {

    private CourseService courseService;
    private UserService userService;

    @GetMapping("/api/courses-for-user")
    public List<CourseResponse> getCoursesForUser() {
        UserResponse user = userService.getAuthenticatedUser();
        return userService.getCourses(user.getUuid());
    }

    @GetMapping("/api/all-courses")
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllWithUsers();
    }
}
