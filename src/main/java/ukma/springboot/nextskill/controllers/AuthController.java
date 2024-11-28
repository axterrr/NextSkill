package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.UserService;
import ukma.springboot.nextskill.models.views.UserView;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired
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
        return "redirect:/home";
    }


    @GetMapping("register")
    public String register() {

        return "register";
    }

}
