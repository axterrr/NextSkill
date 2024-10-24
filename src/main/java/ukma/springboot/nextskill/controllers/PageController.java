package ukma.springboot.nextskill.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("page")
public class PageController {

    private UserService userService;
    private CourseService courseService;

    @GetMapping("/user/all")
    public String allUsersPage(Model model) {
        List<UserDto> users = userService.getAllUsers().stream().map(UserMapper::toUserDto).toList();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/form")
    public String getUserForm(Model model, @RequestParam(required = false) UUID id) {
        UserDto user = id == null ? new UserDto() : UserMapper.toUserDto(userService.getUser(id));
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/user/form/submit")
    public String postUserForm(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) return "userForm";
        userService.processUser(UserMapper.toUser(user));
        return "redirect:/page/user/all";

    }

    @GetMapping("/course/all")
    public String allCoursesPage(Model model) {
        List<CourseDto> courses = courseService.getAllCourses().stream().map(CourseMapper::toCourseDto).toList();
        model.addAttribute("courses", courses);
        return "courses";
    }
}
