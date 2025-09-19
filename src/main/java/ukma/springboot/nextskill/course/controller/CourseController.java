package ukma.springboot.nextskill.course.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.course.CourseService;
import ukma.springboot.nextskill.common.models.responses.CourseResponse;
import ukma.springboot.nextskill.common.models.responses.UserResponse;
import ukma.springboot.nextskill.common.models.views.CourseView;
import ukma.springboot.nextskill.common.models.views.SectionView;
import ukma.springboot.nextskill.course.SectionCreateEvent;
import ukma.springboot.nextskill.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CourseController {

    private static final String REDIRECT_TO_COURSE = "redirect:/course/";
    private static final String COURSE = "course";
    private UserService userService;
    private CourseService courseExternalAPI;
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "home";
    }

    @GetMapping("course/{courseUuid}")
    public String course(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        model.addAttribute(COURSE, courseExternalAPI.getWithSectionsWithPostsAndTests(courseUuid));

        CourseResponse course = courseExternalAPI.getWithSectionsWithPostsAndTests(courseUuid);

        boolean hasOwnerRights = courseExternalAPI.hasOwnerRights(user.getUuid(), courseUuid);
        boolean isEnrolled = courseExternalAPI.isEnrolled(courseUuid, user.getUuid());
        boolean isAdmin = userService.isAdmin(user.getUuid());
        boolean isStudent = userService.isStudent(user.getUuid());

        model.addAttribute("isOwner", hasOwnerRights);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isStudent", isStudent);
        model.addAttribute("isEnrolled", isEnrolled);

        model.addAttribute(COURSE, course);
        model.addAttribute("user", user);

        return COURSE;
    }

    @GetMapping("course/{courseUuid}/enrolledStudents")
    public String enrolledStudents(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseExternalAPI.getWithUsers(courseUuid));
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "enrolledStudents";
    }

    @GetMapping("course/{courseUuid}/unroll/{studentUuid}")
    public String unroll(@PathVariable UUID courseUuid, @PathVariable UUID studentUuid, Model model) {
        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseExternalAPI.hasOwnerRights(authenticated.getUuid(), courseUuid);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseUuid;

        courseExternalAPI.unrollStudent(courseUuid, studentUuid);
        model.addAttribute(COURSE, courseExternalAPI.getWithUsers(courseUuid));
        model.addAttribute("user", authenticated);
        return REDIRECT_TO_COURSE + courseUuid + "/enrolledStudents?unrolled";
    }

    @GetMapping("course/{courseUuid}/enroll")
    public String enroll(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        courseExternalAPI.enrollStudent(courseUuid, user.getUuid());
        model.addAttribute(COURSE, courseExternalAPI.getWithUsers(courseUuid));
        model.addAttribute("user", user);
        return REDIRECT_TO_COURSE + courseUuid + "?enrolled";
    }

    @GetMapping("course/{courseUuid}/unroll")
    public String unroll(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        courseExternalAPI.unrollStudent(courseUuid, user.getUuid());
        model.addAttribute(COURSE, courseExternalAPI.getWithUsers(courseUuid));
        model.addAttribute("user", user);
        return REDIRECT_TO_COURSE + courseUuid + "?unrolled";
    }

    @GetMapping("/all-courses")
    public String allCourses(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "allCourses";
    }

    @PostMapping("course/{courseUuid}/delete")
    public String deleteCourse(@PathVariable UUID courseUuid) {
        courseExternalAPI.delete(courseUuid);
        return "redirect:/home?course&deleted";
    }

    @GetMapping("/course/{courseUuid}/edit")
    public String editCourse(
            @PathVariable(name = "courseUuid") String courseUuid,
            Model model
    ) {
        UUID courseId = UUID.fromString(courseUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseExternalAPI.hasOwnerRights(authenticated.getUuid(), courseId);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseUuid;

        model.addAttribute(COURSE, courseExternalAPI.get(courseId));
        model.addAttribute("user", authenticated);

        return "edit-course";
    }

    @PostMapping("/course/{courseUuid}/edit")
    public String editCourse(
            @PathVariable(name = "courseUuid") String courseUuid,
            @ModelAttribute CourseView courseView
    ) {
        UUID courseId = UUID.fromString(courseUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseExternalAPI.hasOwnerRights(authenticated.getUuid(), courseId);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseId;

        courseView.setUuid(courseId);
        courseExternalAPI.update(courseView);

        return REDIRECT_TO_COURSE + courseId;
    }

    @GetMapping("course/{courseUuid}/addSection")
    public String addSection(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute(COURSE, courseExternalAPI.get(courseUuid));
        return "add-section";
    }

    @PostMapping("course/{courseUuid}/addSection")
    public String addSection(@PathVariable UUID courseUuid,
            @RequestParam String name,
            @RequestParam(required = false) String description)
    {
        SectionView sectionView = SectionView.builder()
                .name(name)
                .description(description)
                .courseId(courseUuid)
                .build();

        eventPublisher.publishEvent(new SectionCreateEvent(this, sectionView));

        return REDIRECT_TO_COURSE + courseUuid + "?section&added";
    }

    @GetMapping("course/add")
    public String showAddCoursePage(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "add-course";
    }

    @PostMapping("course/add")
    public String addCourse(
            @RequestParam String name,
            @RequestParam(required = false) String description)
            {

        CourseView courseView = CourseView.builder()
                .name(name)
                .description(description)
                .teacherId(userService.getAuthenticatedUser().getUuid())
                .build();

                courseExternalAPI.create(courseView);
        return "redirect:/home?course&added";
    }

    @GetMapping("/api/courses-for-user")
    public ResponseEntity<List<CourseResponse>> getCoursesForUser() {
        UserResponse user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(userService.getCourses(user.getUuid()));
    }

    @GetMapping("/api/all-courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseExternalAPI.getAllWithUsers());
    }
}
