package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukma.springboot.nextskill.exceptions.NoAccessException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.mappers.CourseMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.services.UserService;
import ukma.springboot.nextskill.validation.CourseValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final String COURSE = "Course";
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private UserService userService;
    private CourseValidator courseValidator;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream().map(CourseMapper::toCourseResponse).toList();
    }

    @Override
    public CourseResponse get(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(COURSE, id));
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    public CourseResponse create(CourseView courseView) {
        courseValidator.validateForCreation(courseView);
        CourseEntity courseEntity = courseRepository.save(CourseMapper.toCourseEntity(courseView));
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    public CourseResponse update(CourseView courseView) {
        courseValidator.validateForUpdate(courseView);
        CourseEntity existingCourse = courseRepository.findById(courseView.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseView.getUuid()));
        CourseEntity courseEntity = courseRepository.save(CourseMapper.toCourseEntity(courseView, existingCourse));
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    public void delete(UUID id) {
        UserResponse currentUser = userService.getAuthenticatedUser();
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, id));

        if (currentUser.getRole() != UserRole.ADMIN && !courseEntity.getTeacher().getUuid().equals(currentUser.getUuid())) {
            throw new NoAccessException("You do not have permission to delete this course.");
        }

        courseRepository.delete(courseEntity);
    }

    @Override
    public List<CourseResponse> getCoursesWhereStudent(UUID studentId) {
        return null;
    }

    @Override
    public List<CourseResponse> getCoursesWhereTeacher(UUID teacherId) {
        return null;
    }

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseId));
        UserEntity userEntity = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User", studentId));
        courseEntity.getStudents().add(userEntity);
        courseRepository.save(courseEntity);
    }

    @Override
    @Transactional
    public CourseResponse getWithUsers(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, id));
        Hibernate.initialize(courseEntity.getStudents());
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    @Transactional
    public CourseResponse getWithSectionsWithPostsAndTests(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, id));
        courseEntity.getSections().forEach(s -> {
            Hibernate.initialize(s.getPosts());
            Hibernate.initialize(s.getTests());
        });
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    @Transactional
    public List<CourseResponse> getAllWithUsers() {
        List<CourseEntity> courses = courseRepository.findAll();
        courses.forEach(course -> Hibernate.initialize(course.getStudents()));
        return courses.stream()
                .map(CourseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public boolean hasOwnerRights(UUID userUuid, UUID courseUuid) {
        CourseEntity course = courseRepository.findById(courseUuid).orElseThrow(() -> new ResourceNotFoundException("Course", courseUuid));
        UUID courseOwner = course.getTeacher().getUuid();
        return courseOwner.equals(userUuid);
    }

    @Override
    public boolean isEnrolled(UUID courseUuid, UUID studentUuid) {
        CourseEntity courseEntity = courseRepository.findById(courseUuid)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseUuid));
        Hibernate.initialize(courseEntity.getStudents());
        UserEntity userEntity = userRepository.findById(studentUuid)
                .orElseThrow(() -> new ResourceNotFoundException("User", studentUuid));
        return (courseEntity.getStudents().contains(userEntity));
    }


}
