package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.CourseObject;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseLimitService courseLimitService;
    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CourseLimitService courseLimitService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseLimitService = courseLimitService;
    }

    @Override
    public Course getCourse(UUID id) {
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("Course", id.toString());
        return CourseMapper.toCourse(result.get());
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseMapper::toCourse).toList();
    }

    @Override
    public Course createCourse(Course course) {
        checkTeacherExisting(course);
        CourseEntity courseEntity = CourseMapper.toCourseEntity(course);
        CourseEntity savedEntity = courseRepository.save(courseEntity);
        return CourseMapper.toCourse(savedEntity);
    }

    @Override
    public Course updateCourse(UUID id, Course updatedCourse) {
        Optional<CourseEntity> existingCourse = courseRepository.findById(id);
        if (existingCourse.isEmpty()) throw new ResourceNotFoundException("Course", id.toString());
        checkTeacherExisting(updatedCourse);
        updatedCourse.setUuid(existingCourse.get().getUuid());
        CourseEntity result = courseRepository.save(CourseMapper.toCourseEntity(updatedCourse));
        return CourseMapper.toCourse(result);
    }

    @Override
    public void deleteCourse(UUID id) {
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("Course", id.toString());
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

    @Override
    public void enrollUserToCourse(UUID courseId, UUID userId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId.toString()));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        int currentCoursesCount = user.getCourses().size();

        courseLimitService.checkCourseLimit(currentCoursesCount);

        course.getUsers().add(user);
        courseRepository.save(course);
    }

    private void checkTeacherExisting(Course course) {
        Optional<UserEntity> teacher = userRepository.findById(course.getTeacher().getUuid());
        if (teacher.isEmpty() || teacher.get().getRoles().stream().noneMatch(role -> role.getTitle().equals("TEACHER"))) {
            throw new ResourceNotFoundException("Teacher", course.getTeacher().getUuid().toString());
        }
    }

}
