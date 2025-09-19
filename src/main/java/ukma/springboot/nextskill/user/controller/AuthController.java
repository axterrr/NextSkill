package ukma.springboot.nextskill.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.views.UserView;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.user.UserService;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserView userView) {
        userService.create(userView);
        return "redirect:/login";
    }

    @GetMapping("register")
    public String register() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "register";
    }

}
