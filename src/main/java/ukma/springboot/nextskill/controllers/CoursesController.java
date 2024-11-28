package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.models.views.TestView;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.SectionService;
import ukma.springboot.nextskill.services.UserService;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class CoursesController {

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

        model.addAttribute("course", course);
        model.addAttribute("user", user);

        return COURSE;
    }

    @GetMapping("course/{courseUuid}/enrolledStudents")
    public String enrolledStudents(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "enrolledStudents";
    }

    @GetMapping("course/{courseUuid}/enroll")
    public String enroll(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        courseService.enrollStudent(courseUuid, userService.getAuthenticatedUser().getUuid());
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "redirect:/course?enrolled";
    }

    @GetMapping("course/{courseUuid}/unroll")
    public String unroll(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseService.getWithUsers(courseUuid));
        courseService.unrollStudent(courseUuid, userService.getAuthenticatedUser().getUuid());
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "redirect:/course?unrolled";
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
/*
    @GetMapping("course/{courseUuid}/edit")
    public String editCourse(@PathVariable UUID courseUuid, Model model) {
        //courseService.update(courseUuid);
        model.addAttribute(COURSE, courseService.get(courseUuid));
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "edit-course";
    }

    @PostMapping("course/{courseUuid}/edit")
    public ResponseEntity<String> editCourse(
            @PathVariable UUID courseUuid,
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        CourseView courseView = CourseView.builder()
                .name(name)
                .description(description)
                .uuid(courseUuid)
                .teacherId(userService.getAuthenticatedUser().getUuid())
                .build();

        courseService.create(courseView);
        courseService.update(courseView);
        return ResponseEntity.ok("Course added successfully!");
    }*/
@GetMapping("/course/{courseUuid}/edit")
public String editCourse(
        @PathVariable(name = "courseUuid") String courseUuid,
        Model model
) {
    UUID courseId = UUID.fromString(courseUuid);

    UserResponse authenticated = userService.getAuthenticatedUser();
    boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
    if(!isOwner && userService.isAdmin(authenticated.getUuid()))
        return "redirect:/course/" + courseUuid;

    model.addAttribute("course", courseService.get(courseId));
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
            return "redirect:/course/" + courseId;

        courseView.setUuid(courseId);
        courseService.update(courseView);

        return "redirect:/course/" + courseId;
    }

    @GetMapping("course/{courseUuid}/addSection")
    public String addSection(@PathVariable UUID courseUuid, Model model) {
        model.addAttribute(COURSE, courseService.get(courseUuid));
        return "add-section";
    }

    @PostMapping("course/{courseUuid}/addSection")
    public ResponseEntity<String> addSection(@PathVariable UUID courseUuid,
            @RequestParam String name,
            @RequestParam(required = false) String description)
    {
        SectionView sectionView = SectionView.builder()
                .name(name)
                .description(description)
                .courseId(courseUuid)
                .build();

        sectionService.create(sectionView);

        return ResponseEntity.ok("Section added successfully!");
    }


//    @PostMapping("/course/{courseUuid}/edit")
//    public String editCourseSection(
//            @PathVariable(name = "courseUuid") String courseUuid,
//            @ModelAttribute TestView testView
//    ) {
//        UUID courseId = UUID.fromString(courseUuid);
//
//        UserResponse authenticated = userService.getAuthenticatedUser();
//        boolean isOwner = courseService.hasOwnerRights(authenticated.getUuid(), courseId);
//        if (!isOwner && !userService.isAdmin(authenticated.getUuid()))
//            return "redirect:/course/"  + courseUuid;
//
////        sectionService.set(courseId));
////        courseService.update(ciu);
//
//        return "redirect:/course/" + courseUuid;
//    }




    @GetMapping("courses/add")
    public String showAddCoursePage(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "add-course"; // Points to add-course.html
    }

    @PostMapping("courses/add")
    public ResponseEntity<String> addCourse(
            @RequestParam String name,
            @RequestParam(required = false) String description)
            {

        CourseView courseView = CourseView.builder()
                .name(name)
                .description(description)
                .teacherId(userService.getAuthenticatedUser().getUuid())
                .build();

        courseService.create(courseView);
        return ResponseEntity.ok("Course added successfully!");
    }
}
