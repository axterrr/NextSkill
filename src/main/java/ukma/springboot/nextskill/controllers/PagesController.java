package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.UserMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;
import java.util.UUID;

@Controller
public class PagesController {

    UserService userService;
    CourseService courseService;

    @GetMapping("home")
    public String home(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        List<CourseResponse> courses = courseService.getCoursesWhereStudent(user.getUuid());

        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("course/{courseUuid}")
    public String course(@PathVariable UUID courseUuid, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);
        UserMapper.toUserResponse(user);

        CourseResponse course = courseService.getWithSectionsWithPosts(courseUuid);

        model.addAttribute("course", course);
        model.addAttribute("user", user);
        model.addAttribute("isEnrolled", courseService.isEnrolled(courseUuid, user.getUuid()));
        return "course";
    }

    @GetMapping("course/{courseUuid}/enrolledStudents")
    public String enrolledStudents(@PathVariable UUID courseUuid, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);
        UserMapper.toUserResponse(user);

        CourseResponse course = courseService.getWithUsers(courseUuid);

        model.addAttribute("course", course);
        model.addAttribute("user", user);
        return "enrolledStudents";
    }

    @GetMapping("/all-courses")
    public String allCourses(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "allCourses";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
