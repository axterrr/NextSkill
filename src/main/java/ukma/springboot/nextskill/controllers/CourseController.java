package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/courses-for-student")
    public List<CourseResponse> getCoursesForStudent() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        List<CourseResponse> courses = courseService.getCoursesWhereStudent(user.getUuid());
        return courses;
    }

    @GetMapping("/api/courses-for-teacher")
    public List<CourseResponse> getCoursesForTeacher() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        List<CourseResponse> courses = courseService.getCoursesWhereTeacher(user.getUuid());
        return courses;
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
        List<CourseResponse> courses = courseService.getAllWithUsers();
        return courses;
    }
}
