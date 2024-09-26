package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ukma.springboot.nextskill.entities.AssignmentEntity;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.entities.FileUploadEntity;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.model.Assignment;
import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.CourseObject;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.AssignmentMapper;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import ukma.springboot.nextskill.model.mappers.FileUploadMapper;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course getCourse(UUID id) {
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) throw new IllegalArgumentException("Course not found with id: " + id);

        return CourseMapper.toCourse(result.get());
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll()
                .stream().map(CourseMapper::toCourse).toList();
    }

    @Override
    public Course createCourse(Course course) {
        CourseEntity courseEntity = CourseMapper.toCourseEntity(course);
        CourseEntity savedEntity = courseRepository.save(courseEntity);
        return CourseMapper.toCourse(savedEntity);
    }

    @Override
    public Course updateCourse(UUID id, Course updatedCourse) {

        Optional<CourseEntity> existingCourse = courseRepository.findById(id);
        if (existingCourse.isEmpty()) throw new IllegalArgumentException("Course not found with id: " + id);

        CourseEntity existingCourseEntity = existingCourse.get();

        existingCourseEntity.setName(updatedCourse.getName());
        existingCourseEntity.setDescription(updatedCourse.getDescription());
        existingCourseEntity.setTeacher(UserMapper.toUserEntity(updatedCourse.getTeacher()));

        return CourseMapper.toCourse(courseRepository.save(existingCourseEntity));
    }

    @Override
    public void deleteCourse(UUID id) {
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) throw new IllegalArgumentException("Course not found with id: " + id);
        courseRepository.deleteById(id);
    }

    @Override
    public CourseObject addCourseItem(CourseObject courseObject) {
        return null;
    }

    @Override
    public List<CourseObject> getAllCourseItems() {
        return null;
    }

    @Override
    public CourseObject getCourseItemById(UUID courseObjectId) {
        return null;
    }

    @Override
    public void removeCourseItem(UUID courseObjectId) {
    }

    // Метод запису користувача на курс
    public void enrollUserToCourse(UUID courseId, UUID userId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        course.getUsers().add(user);
        courseRepository.save(course);
    }
}
