package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.PostView;
import ukma.springboot.nextskill.models.views.UserView;
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
        if (!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return "redirect:/course/" + courseId;

        postService.delete(postId);

        return "redirect:/course/" + courseId + "?post&deleted";
    }

    @GetMapping("/section/{sectionId}/{courseId}/addPost")
    public String showPostForm(@PathVariable("sectionId") UUID sectionId, @PathVariable("courseId") UUID courseId, Model model) {
        model.addAttribute("post", PostView.builder().sectionId(sectionId).build());
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("courseId", courseId);
        return "add-post";
    }

    @PostMapping("/section/{sectionId}/addPost")
    public String addPostToSection(@PathVariable UUID sectionId,
                                   @RequestParam String name,
                                   @RequestParam String content,
                                   @RequestParam(required = false) Boolean isHidden,
                                   @RequestParam UUID courseId) {
        return "redirect:/course/" + courseId;
    }



}
