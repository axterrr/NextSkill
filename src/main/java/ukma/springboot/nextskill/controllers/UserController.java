package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.modules.user.UserExternalAPI;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserController {

    private UserExternalAPI userExternalAPI;

    @GetMapping("/profile")
    public String profile(Model model) {
        UserResponse user = userExternalAPI.getAuthenticatedUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("user", userExternalAPI.getWithCourses(user.getUuid()));
        return "profile";
    }

    @GetMapping("user/{id}")
    public String getUser(@PathVariable UUID id, Model model) {
        UserResponse user = userExternalAPI.getResponse(id);
        model.addAttribute("currentUser", userExternalAPI.getAuthenticatedUser());
        model.addAttribute("user", userExternalAPI.getWithCourses(user.getUuid()));
        return "profile";
    }

    @PostMapping("user/{id}/update")
    public String updateUser(@PathVariable UUID id, UserView userView) {
        userView.setUuid(id);
        userExternalAPI.update(userView);
        return "redirect:/profile";
    }

    @PostMapping("user/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {
        userExternalAPI.delete(id);
        return "redirect:/home?user&deleted";
    }
}
