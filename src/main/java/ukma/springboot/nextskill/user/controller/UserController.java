package ukma.springboot.nextskill.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.user.UserService;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("user", userService.getWithCourses(user.getUuid()));
        return "profile";
    }

    @GetMapping("user/{id}")
    public String getUser(@PathVariable UUID id, Model model) {
        UserResponse user = userService.getResponse(id);
        model.addAttribute("currentUser", userService.getAuthenticatedUser());
        model.addAttribute("user", userService.getWithCourses(user.getUuid()));
        return "profile";
    }

    @PostMapping("user/{id}/update")
    public String updateUser(@PathVariable UUID id, UserView userView) {
        userView.setUuid(id);
        userService.update(userView);
        return "redirect:/profile";
    }

    @PostMapping("user/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return "redirect:/home?user&deleted";
    }
}
