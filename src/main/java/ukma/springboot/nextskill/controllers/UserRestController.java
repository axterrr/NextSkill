package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.mappers.CourseMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;


import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserRestController {
    private CourseService courseService;
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.getUserByUsername(username);

        if (user.getRole() == UserRole.TEACHER) {
            List<CourseResponse> ownCourses = courseService.getCoursesWhereTeacher(user.getUuid());
            List<CourseEntity> courseEntities = ownCourses.stream()
                    .map(courseResponse -> CourseMapper.toCourseEntity(courseResponse)) // Мапінг CourseResponse -> CourseEntity
                    .toList();
            user.setOwnCourses(courseEntities);
        } else if (user.getRole() == UserRole.STUDENT) {
            List<CourseResponse> courses = courseService.getCoursesWhereStudent(user.getUuid());
            List<CourseEntity> courseEntities = courses.stream()
                    .map(courseResponse -> CourseMapper.toCourseEntity(courseResponse)) // Мапінг CourseResponse -> CourseEntity
                    .toList();
            user.setCourses(courseEntities);
        }

        model.addAttribute("user", user);
        return "profile";
    }
}
