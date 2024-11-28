package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.PostService;
import ukma.springboot.nextskill.services.UserService;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private CourseService courseService;
    private UserService userService;

    @PostMapping("/post/{postUuid}/delete")
    public String deleteTest(@PathVariable String postUuid) {
        UUID postId = UUID.fromString(postUuid);
        UUID courseId = postService.get(postId).getSection().getCourse().getUuid();

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return "redirect:/course/" + courseId;

        postService.delete(postId);

        return "redirect:/course/" + courseId + "?post&deleted";
    }
}
