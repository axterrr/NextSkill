package ukma.springboot.nextskill.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ukma.springboot.nextskill.modules.course.CourseExternalAPI;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.modules.section.SectionExternalAPI;
import ukma.springboot.nextskill.modules.user.UserExternalAPI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private UserExternalAPI userExternalAPI;

    @Mock
    private CourseExternalAPI courseExternalAPI;

    @Mock
    private SectionExternalAPI sectionExternalAPI;

    @Mock
    private Model model;

    @InjectMocks
    private CoursesController coursesController;

    private UserResponse mockUser;
    private CourseResponse mockCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        mockUser = UserResponse.builder()
                .uuid(UUID.randomUUID())
                .username("test_user")
                .build();

        mockCourse = CourseResponse.builder()
                .uuid(UUID.randomUUID())
                .name("Test Course")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .teacher(mockUser)
                .students(List.of(mockUser))
                .build();
    }

    @Test
    void testHome() {
        when(userExternalAPI.getAuthenticatedUser()).thenReturn(mockUser);

        String viewName = coursesController.home(model);

        verify(userExternalAPI).getAuthenticatedUser();
        verify(model).addAttribute("user", mockUser);

        assertEquals("home", viewName, "The returned view name should be 'home'");
    }

    @Test
    void testEnroll() {
        UUID courseUuid = mockCourse.getUuid();
        when(userExternalAPI.getAuthenticatedUser()).thenReturn(mockUser);

        String viewName = coursesController.enroll(courseUuid, model);

        verify(userExternalAPI).getAuthenticatedUser();
        verify(courseExternalAPI).enrollStudent(courseUuid, mockUser.getUuid());

        assertEquals("redirect:/course/" + courseUuid + "?enrolled", viewName, "The returned view should redirect to the course page with enrolled query.");
    }

    @Test
    void testUnroll() {
        UUID courseUuid = mockCourse.getUuid();
        when(userExternalAPI.getAuthenticatedUser()).thenReturn(mockUser);

        String viewName = coursesController.unroll(courseUuid, model);

        verify(userExternalAPI).getAuthenticatedUser();
        verify(courseExternalAPI).unrollStudent(courseUuid, mockUser.getUuid());

        assertEquals("redirect:/course/" + courseUuid + "?unrolled", viewName, "The returned view should redirect to the course page with unrolled query.");
    }
}
