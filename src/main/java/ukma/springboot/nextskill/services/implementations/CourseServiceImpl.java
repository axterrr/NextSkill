package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.CourseMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.CourseService;
import ukma.springboot.nextskill.validation.CourseValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private CourseValidator courseValidator;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream().map(CourseMapper::toCourseResponse).toList();
    }

    @Override
    public CourseResponse get(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseView.getUuid()));
        CourseEntity courseEntity = courseRepository.save(CourseMapper.toCourseEntity(courseView, existingCourse));
        return CourseMapper.toCourseResponse(courseEntity);
    }

    @Override
    public void delete(UUID id) {
        courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", id));
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> getCoursesWhereStudent(UUID studentId) {
        List<CourseEntity> courses = courseRepository.findByStudentsUuid(studentId);
        return courses.stream()
                .map(CourseMapper::toCourseResponse)
                .toList();
    }

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId));
        UserEntity userEntity = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User", studentId));
        courseEntity.getStudents().add(userEntity);
        courseRepository.save(courseEntity);
    }

    @Override
    public List<CourseResponse> getCoursesWhereTeacher(UUID teacherId) {
        List<CourseEntity> courses = courseRepository.findByTeacherUuid(teacherId);
        return courses.stream()
                .map(CourseMapper::toCourseResponse)
                .toList();
    }

}
