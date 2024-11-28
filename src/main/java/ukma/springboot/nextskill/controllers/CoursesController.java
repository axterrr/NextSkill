package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.responses.UserResponse;
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
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "home";
    }

    @GetMapping("course/{courseUuid}")
    public String course(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        model.addAttribute(COURSE, courseService.getWithSectionsWithPostsAndTests(courseUuid));
        model.addAttribute("user", user);
        model.addAttribute("isEnrolled", courseService.isEnrolled(courseUuid, user.getUuid()));
        return COURSE;
    }

    @GetMapping("course/{courseUuid}/enrolledStudents")
    public String enrolledStudents(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "enrolledStudents";
    }

    @GetMapping("/all-courses")
    public String allCourses(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "allCourses";
    }

    @PostMapping("course/{courseUuid}")
    public String deleteCourse(@PathVariable UUID courseUuid) {
        courseService.delete(courseUuid);
        return "redirect:/home?course&deleted";
    }
}
