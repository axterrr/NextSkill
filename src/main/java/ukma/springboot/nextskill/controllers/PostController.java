package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.course.CourseExternalAPI;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.PostView;
import ukma.springboot.nextskill.post.PostExternalAPI;
import ukma.springboot.nextskill.section.SectionExternalAPI;
import ukma.springboot.nextskill.user.UserExternalAPI;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class PostController {

    private static final String REDIRECT_TO_COURSE = "redirect:/course";
    private PostExternalAPI postExternalAPI;
    private CourseExternalAPI courseExternalAPI;
    private UserExternalAPI userExternalAPI;
    private SectionExternalAPI sectionExternalAPI;

    @PostMapping("/post/{postUuid}/delete")
    public String deleteTest(@PathVariable String postUuid) {
        UUID postId = UUID.fromString(postUuid);
        UUID courseId = postExternalAPI.get(postId).getSection().getCourse().getUuid();

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = courseExternalAPI.hasOwnerRights(authenticated.getUuid(), courseId);
        if (!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_COURSE + courseId;

        postExternalAPI.delete(postId);

        return "redirect:/course/" + courseId + "?post&deleted";
    }

    @PostMapping("/post/add")
    public String addPostToSection(@ModelAttribute PostView postView) {
        SectionResponse associatedSection = sectionExternalAPI.get(postView.getSectionId());
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();

        if ( authenticated.getRole() != UserRole.ADMIN &&
                !associatedSection.getCourse().getTeacher().getUuid().equals(authenticated.getUuid())
        ) {
            return "redirect:/home";
        }

        PostView view = PostView.builder()
                .sectionId(postView.getSectionId())
                .name(postView.getName())
                .content(postView.getContent())
                .isHidden(postView.getIsHidden())
                .build();

        PostResponse res = postExternalAPI.create(view);

        return "redirect:/post/" + res.getUuid();
    }

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable UUID postId, Model model) {
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        PostResponse postResponse = postExternalAPI.get(postId);
        model.addAttribute("user",authenticated);
        model.addAttribute("post", postResponse);
        return "post";
    }

    @GetMapping("/post/{postUuid}/edit")
    public String getPostEditView(
            @PathVariable(name="postUuid") UUID postId,
            Model model
    ) {
        PostResponse post = postExternalAPI.get(postId);

        SectionResponse associatedSection = sectionExternalAPI.get(post.getSection().getUuid());
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();

        if ( authenticated.getRole() != UserRole.ADMIN &&
                !associatedSection.getCourse().getTeacher().getUuid().equals(authenticated.getUuid())
        ) {
            return REDIRECT_TO_COURSE+ associatedSection.getCourse().getUuid();
        }

        model.addAttribute("post", post);

        return "edit-post";
    }

    @PostMapping("/post/{postUuid}/edit")
    public String addPostToSection(
            @PathVariable(name="postUuid") UUID postId,
            @ModelAttribute PostView postView
    ) {
        SectionResponse associatedSection = sectionExternalAPI.get(postView.getSectionId());
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();

        if ( authenticated.getRole() != UserRole.ADMIN &&
                !associatedSection.getCourse().getTeacher().getUuid().equals(authenticated.getUuid())
        ) {
            return "redirect:/home";
        }

        PostView view = PostView.builder()
                .uuid(postId)
                .sectionId(postView.getSectionId())
                .name(postView.getName())
                .content(postView.getContent())
                .isHidden(postView.getIsHidden())
                .build();

        PostResponse res = postExternalAPI.update(view);

        return "redirect:/post/" + res.getUuid();
    }
}
