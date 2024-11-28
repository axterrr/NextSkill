package ukma.springboot.nextskill.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ukma.springboot.nextskill.exceptions.NoAccessException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.services.implementations.CourseServiceImpl;
import ukma.springboot.nextskill.validation.CourseValidator;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTests {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @Mock
    private CourseValidator courseValidator;

    @InjectMocks
    private CourseServiceImpl courseService;

    private UUID courseId;
    private CourseEntity courseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        courseEntity = new CourseEntity();
        courseEntity.setUuid(courseId);
    }
    @Test
    void testGetCourseSuccess() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        CourseResponse response = courseService.get(courseId);

        assertNotNull(response);
        assertEquals(courseId, response.getUuid());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testGetCourseNotFound() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.get(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testCreateCourseSuccess() {
        CourseView courseView = new CourseView();
        CourseEntity savedEntity = new CourseEntity();
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(savedEntity);

        CourseResponse response = courseService.create(courseView);

        assertNotNull(response);
        verify(courseValidator, times(1)).validateForCreation(courseView);
        verify(courseRepository, times(1)).save(any(CourseEntity.class));
    }

    @Test
    void testUpdateCourseNotFound() {
        CourseView courseView = new CourseView();
        courseView.setUuid(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.update(courseView));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testDeleteCourseNoAccess() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        doThrow(new NoAccessException("You do not have permission to delete this course."))
                .when(userService).getAuthenticatedUser();

        assertThrows(NoAccessException.class, () -> courseService.delete(courseId));
    }

    @Test
    void testDeleteCourseSuccess() {
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(UserRole.ADMIN);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(userService.getAuthenticatedUser()).thenReturn(userResponse);

        assertDoesNotThrow(() -> courseService.delete(courseId));

        verify(courseRepository, times(1)).delete(courseEntity);
    }
}
