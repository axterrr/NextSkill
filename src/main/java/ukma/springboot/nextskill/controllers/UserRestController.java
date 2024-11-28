package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.mappers.CourseMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;


import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserRestController {
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        if (user.getRole() == UserRole.TEACHER) {
            List<CourseResponse> ownCourses = userService.getOwnCourses(user.getUuid());
            ;
            List<CourseEntity> courseEntities = ownCourses.stream()
                    .map(courseResponse -> CourseMapper.toCourseEntity(courseResponse))
                    .toList();
            user.setOwnCourses(courseEntities);
        } else if (user.getRole() == UserRole.STUDENT) {
            List<CourseResponse> courses = userService.getCourses(user.getUuid());
            List<CourseEntity> courseEntities = courses.stream()
                    .map(courseResponse -> CourseMapper.toCourseEntity(courseResponse))
                    .toList();
            user.setCourses(courseEntities);
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        UserResponse user = userService.get(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserView userView) {
        UserResponse user = userService.create(userView);
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UserView userView) {
        userView.setUuid(id);
        UserResponse updatedUser = userService.update(userView);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("user/{id}/edit-description")
    public ResponseEntity<UserResponse> editDescription(@PathVariable UUID id, @RequestBody UserView userView) {
        userView.setUuid(id);
        UserResponse updatedUser = userService.update(userView);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("user/{id}/edit-name")
    public ResponseEntity<UserResponse> editName(@PathVariable UUID id, @RequestBody UserView userView) {
        userView.setUuid(id);
        UserResponse updatedUser = userService.update(userView);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("user/{id}/edit-email")
    public ResponseEntity<UserResponse> editEmail(@PathVariable UUID id, @RequestBody UserView userView) {
        userView.setUuid(id);
        UserResponse updatedUser = userService.update(userView);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("user/{id}/edit-phone")
    public ResponseEntity<UserResponse> editPhone(@PathVariable UUID id, @RequestBody UserView userView) {
        userView.setUuid(id);
        UserResponse updatedUser = userService.update(userView);
        return ResponseEntity.ok(updatedUser);
    }
}
