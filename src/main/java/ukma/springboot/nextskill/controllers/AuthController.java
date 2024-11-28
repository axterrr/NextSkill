package ukma.springboot.nextskill.controllers;

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

    private UserService userService;

    @GetMapping("login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "login";
    }


//        @PostMapping("/register")
//        public ResponseEntity<UserResponse> registerUser (@RequestBody UserView userView){
//            UserResponse newUser = userService.create(userView);
//            return ResponseEntity.status(201).body(newUser);
//        }

        @GetMapping("register")
        public String register() {

            return "register";
        }

}
