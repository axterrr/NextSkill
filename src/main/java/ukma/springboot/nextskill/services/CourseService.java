package ukma.springboot.nextskill.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.logging.markers.CompositeLogMarkers;
import ukma.springboot.nextskill.logging.markers.LogMarkers;
import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.CourseObject;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.security.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseLimitService courseLimitService;
    private static final Logger logger = LogManager.getLogger(CourseService.class);

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CourseLimitService courseLimitService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseLimitService = courseLimitService;
    }

    @Override
    public Course getCourse(UUID id) {
        logger.info(LogMarkers.COURSE_MARKER, "Trying to fetch course with id: {}", id);
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Course", id.toString());
        }
        logger.info(LogMarkers.COURSE_MARKER, "Course with id: {} fetched successfully", id);
        return CourseMapper.toCourse(result.get());
    }

    @Override
    public List<Course> getAllCourses() {
        logger.info(LogMarkers.COURSE_MARKER, "Fetching all courses");
        return courseRepository.findAll().stream().map(CourseMapper::toCourse).toList();
    }

    @Override
    public Course createCourse(Course course) {
        logger.info(LogMarkers.COURSE_MARKER, "Trying to create course with name: {} in session {}", course.getName(), ThreadContext.get("sessionInfo"));

        checkTeacherExisting(course);

        logger.debug("Converting Course model to CourseEntity for saving.");
        CourseEntity courseEntity = CourseMapper.toCourseEntity(course);

        logger.debug("Saving the course to the repository.");
        CourseEntity savedEntity = courseRepository.save(courseEntity);

        logger.info(CompositeLogMarkers.COURSE_CREATE_MARKER, "Course with name: {} created successfully", course.getName());
        return CourseMapper.toCourse(savedEntity);
    }

    @Override
    public Course updateCourse(UUID id, Course updatedCourse) {
        logger.info(LogMarkers.COURSE_MARKER, "Trying to update course with id: {}", id);
        Optional<CourseEntity> existingCourse = courseRepository.findById(id);
        if (existingCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course", id.toString());
        }

        checkTeacherExisting(updatedCourse);
        updatedCourse.setUuid(existingCourse.get().getUuid());
        CourseEntity result = courseRepository.save(CourseMapper.toCourseEntity(updatedCourse));
        logger.info(LogMarkers.COURSE_MARKER, "Course with id {} updated successfully", id);
        return CourseMapper.toCourse(result);
    }

    @Override
    public void deleteCourse(UUID id) {
        logger.info(LogMarkers.COURSE_MARKER, "Trying to delete course with id: {}", id);
        Optional<CourseEntity> result = courseRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Course", id.toString());
        }
        courseRepository.deleteById(id);
        logger.info(LogMarkers.COURSE_MARKER, "Course with id {} deleted successfully", id);
    }

    @Override
    public CourseObject addCourseItem(CourseObject courseObject) {
        logger.warn("{} is not implemented yet.", Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public List<CourseObject> getAllCourseItems() {
        logger.warn("{} is not implemented yet.", Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public CourseObject getCourseItemById(UUID courseObjectId) {
        logger.warn("{} is not implemented yet.", Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public void removeCourseItem(UUID courseObjectId) {
        logger.warn("{} is not implemented yet.", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public void enrollUserToCourse(UUID courseId, UUID userId) {
        logger.info(LogMarkers.COURSE_MARKER, "Trying to enroll user {} to course {}", userId, courseId);

        logger.debug("Checking if course {} exists in the repository.", courseId);
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId.toString()));

        logger.debug("Checking if user {} exists in the repository.", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        int currentCoursesCount = user.getCourses().size();
        logger.debug("User {} is currently enrolled in {} courses", userId, currentCoursesCount);

        courseLimitService.checkCourseLimit(currentCoursesCount);

        logger.debug("Adding user {} to course {}.", userId, courseId);
        course.getUsers().add(user);
        courseRepository.save(course);
        logger.info(LogMarkers.COURSE_MARKER, "User {} enrolled to course {} successfully", userId, courseId);
    }

    private void checkTeacherExisting(Course course) {
        logger.debug("Checking if teacher exists for course: {}", course.getName());
        Optional<UserEntity> teacher = userRepository.findById(course.getTeacher().getUuid());
        if (teacher.isEmpty() || teacher.get().getUserRole() != UserRole.TEACHER) {
            throw new ResourceNotFoundException("Teacher", course.getTeacher().getUuid().toString());
        }
        logger.debug("Teacher check passed for course: {}", course.getName());
    }

}
