package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;

@Controller
public class PagesController {

    UserService userService;
    CourseService courseService;

    @GetMapping("home")
    public String home(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        model.addAttribute("user", user);
        return "home";
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
