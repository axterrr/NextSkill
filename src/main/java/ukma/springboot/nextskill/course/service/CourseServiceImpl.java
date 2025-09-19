package ukma.springboot.nextskill.course.service;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukma.springboot.nextskill.course.CourseService;
import ukma.springboot.nextskill.course.repository.CourseRepository;
import ukma.springboot.nextskill.common.exceptions.NoAccessException;
import ukma.springboot.nextskill.common.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.email.EmailSendEvent;
import ukma.springboot.nextskill.common.models.entities.CourseEntity;
import ukma.springboot.nextskill.common.models.entities.UserEntity;
import ukma.springboot.nextskill.common.models.enums.UserRole;
import ukma.springboot.nextskill.course.mapper.CourseMapper;
import ukma.springboot.nextskill.common.models.responses.CourseResponse;
import ukma.springboot.nextskill.common.models.responses.UserResponse;
import ukma.springboot.nextskill.common.models.views.CourseView;
import ukma.springboot.nextskill.user.UserService;
import ukma.springboot.nextskill.course.validation.CourseValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final String COURSE = "Course";
    private CourseRepository courseRepository;
    private UserService userService;
    private CourseValidator courseValidator;
    private ApplicationEventPublisher eventPublisher;

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
    public CourseResponse getWithUsers(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, id));
        Hibernate.initialize(courseEntity.getStudents());
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
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
    public List<CourseResponse> getAllWithUsers() {
        List<CourseEntity> courses = courseRepository.findAll();
        courses.forEach(course -> Hibernate.initialize(course.getStudents()));
        return courses.stream()
                .map(CourseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public boolean hasOwnerRights(UUID userUuid, UUID courseUuid) {
        CourseEntity course = courseRepository.findById(courseUuid)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseUuid));
        UUID courseOwner = course.getTeacher().getUuid();
        return courseOwner.equals(userUuid);
    }

    @Override
    public boolean isEnrolled(UUID courseUuid, UUID studentUuid) {
        CourseEntity courseEntity = courseRepository.findById(courseUuid)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseUuid));
        Hibernate.initialize(courseEntity.getStudents());
        UserResponse userEntity = userService.get(studentUuid);
        return (courseEntity.getStudents().contains(userEntity));
    }

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseId));
        UserResponse userEntity = userService.get(studentId);
        if (!isEnrolled(courseId, studentId)) {
            courseEntity.getStudents().add(UserEntity.builder().uuid(studentId).build());
        }
        else throw new IllegalArgumentException("User is already enrolled to course");
        courseRepository.save(courseEntity);

        eventPublisher.publishEvent(new EmailSendEvent(this, userEntity.getEmail(),
                "Enrolling to new course", "You have been enrolled to new course: \"" + courseEntity.getName() + "\""));
    }

    @Override
    public void unrollStudent(UUID courseUuid, UUID studentUuid) {
        CourseEntity courseEntity = courseRepository.findById(courseUuid)
                .orElseThrow(() -> new ResourceNotFoundException(COURSE, courseUuid));
        UserResponse userEntity = userService.get(studentUuid);
        if (isEnrolled(courseUuid, studentUuid))
            courseEntity.getStudents().remove(userEntity);
        else throw new IllegalArgumentException("User is not enrolled to course");
        courseRepository.save(courseEntity);

        eventPublisher.publishEvent(new EmailSendEvent(this, userEntity.getEmail(),
                "Unrolling from course", "You have been unrolled from course: \"" + courseEntity.getName() + "\""));
    }
}
