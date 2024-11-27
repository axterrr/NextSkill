package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.UserMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class CoursesController {

    private static final String COURSE = "course";
    private UserService userService;
    private CourseService courseService;

    @GetMapping("home")
    public String home(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        model.addAttribute("user", UserMapper.toUserResponse(user));
        return "home";
    }

    @GetMapping("course/{courseUuid}")
    public String course(@PathVariable UUID courseUuid, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);
        CourseResponse course = courseService.getWithSectionsWithPostsAndTests(courseUuid);

        model.addAttribute(COURSE, course);
        model.addAttribute("user", UserMapper.toUserResponse(user));
        model.addAttribute("isEnrolled", courseService.isEnrolled(courseUuid, user.getUuid()));
        return COURSE;
    }

    @GetMapping("course/{courseUuid}/enrolledStudents")
    public String enrolledStudents(@PathVariable UUID courseUuid, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);
        CourseResponse course = courseService.getWithUsers(courseUuid);

        model.addAttribute(COURSE, course);
        model.addAttribute("user", UserMapper.toUserResponse(user));
        return "enrolledStudents";
    }

    @GetMapping("/all-courses")
    public String allCourses(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        model.addAttribute("user", UserMapper.toUserResponse(user));
        return "allCourses";
    }
}
