package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.SectionService;
import ukma.springboot.nextskill.services.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CoursesController {

    private static final String REDIRECT_TO_COURSE = "redirect:/course/";
    private static final String COURSE = "course";
    private UserService userService;
    private CourseService courseService;
    private SectionService sectionService;

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "home";
    }

    @GetMapping("course/{courseUuid}")
    public String course(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        model.addAttribute(COURSE, courseService.getWithSectionsWithPostsAndTests(courseUuid));

        CourseResponse course = courseService.getWithSectionsWithPostsAndTests(courseUuid);

        boolean hasOwnerRights = courseService.hasOwnerRights(user.getUuid(), courseUuid);
        boolean isEnrolled = courseService.isEnrolled(courseUuid, user.getUuid());
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
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "enrolledStudents";
    }

    @GetMapping("course/{courseUuid}/unroll/{studentUuid}")
    public String unroll(@PathVariable UUID courseUuid, @PathVariable UUID studentUuid, Model model) {
        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseUuid);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseUuid;

        courseService.unrollStudent(courseUuid, studentUuid);
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        model.addAttribute("user", authenticated);
        return REDIRECT_TO_COURSE + courseUuid + "/enrolledStudents?unrolled";
    }

    @GetMapping("course/{courseUuid}/enroll")
    public String enroll(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        courseService.enrollStudent(courseUuid, user.getUuid());
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        model.addAttribute("user", user);
        return REDIRECT_TO_COURSE + courseUuid + "?enrolled";
    }

    @GetMapping("course/{courseUuid}/unroll")
    public String unroll(@PathVariable UUID courseUuid, Model model) {
        UserResponse user = userService.getAuthenticatedUser();
        courseService.unrollStudent(courseUuid, user.getUuid());
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
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
        courseService.delete(courseUuid);
        return "redirect:/home?course&deleted";
    }

    @GetMapping("/course/{courseUuid}/edit")
    public String editCourse(
            @PathVariable(name = "courseUuid") String courseUuid,
            Model model
    ) {
        UUID courseId = UUID.fromString(courseUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseUuid;

        model.addAttribute(COURSE, courseService.get(courseId));
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
        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
        if(!isOwner && userService.isAdmin(authenticated.getUuid()))
            return REDIRECT_TO_COURSE + courseId;

        courseView.setUuid(courseId);
        courseService.update(courseView);

        return REDIRECT_TO_COURSE + courseId;
    }

    @GetMapping("course/{courseUuid}/addSection")
    public String addSection(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute(COURSE, courseService.get(courseUuid));
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

        sectionService.create(sectionView);

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

        courseService.create(courseView);
        return "redirect:/home?course&added";
    }

    @GetMapping("/api/courses-for-user")
    public ResponseEntity<List<CourseResponse>> getCoursesForUser() {
        UserResponse user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(userService.getCourses(user.getUuid()));
    }

    @GetMapping("/api/all-courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllWithUsers());
    }
}
