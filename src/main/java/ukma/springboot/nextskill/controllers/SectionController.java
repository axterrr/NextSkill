package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.SectionService;
import ukma.springboot.nextskill.services.UserService;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class SectionController {

    private SectionService sectionService;
    private UserService userService;
    private CourseService courseService;

    @GetMapping("section/{sectionUuid}/edit")
    public String editSection(@PathVariable UUID sectionUuid, Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("section", sectionService.get(sectionUuid));
        return "edit-section";
    }

    @PostMapping("/section/{sectionUuid}/edit")
    public String editSection(
            @PathVariable String sectionUuid,
            @ModelAttribute SectionView sectionView
    ) {
        UUID sectionId = UUID.fromString(sectionUuid);
        SectionResponse sectionResponse = sectionService.get(sectionId);
        UUID courseId = sectionResponse.getCourse().getUuid();

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
        if (!isOwner && !userService.isAdmin(authenticated.getUuid()))
            return "redirect:/course/" + courseId;

        sectionView.setUuid(sectionId);
        sectionService.update(sectionView);

        return "redirect:/course/" + courseId + "?section&updated";
    }

    @PostMapping("section/{sectionUuid}/delete")
    public String deleteSection(@PathVariable UUID sectionUuid) {
        SectionResponse section = sectionService.get(sectionUuid);
        sectionService.delete(sectionUuid);
        return "redirect:/course/" + section.getCourse().getUuid() + "?section&deleted";
    }
}
