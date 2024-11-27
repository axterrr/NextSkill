package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;
    private UserService userService;

    @GetMapping("/api/courses-for-student")
    public List<CourseResponse> getCoursesForStudent() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        return courseService.getCoursesWhereStudent(user.getUuid());
    }

    @GetMapping("/api/courses-for-teacher")
    public List<CourseResponse> getCoursesForTeacher() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        return courseService.getCoursesWhereTeacher(user.getUuid());
    }

    @GetMapping("/api/courses-for-user")
    public List<CourseResponse> getCoursesForUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        if (user.getRole() == UserRole.TEACHER) {
            return courseService.getCoursesWhereTeacher(user.getUuid());
        } else{
            return courseService.getCoursesWhereStudent(user.getUuid());
        }
    }

    @GetMapping("/api/all-courses")
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllWithUsers();
    }
}
