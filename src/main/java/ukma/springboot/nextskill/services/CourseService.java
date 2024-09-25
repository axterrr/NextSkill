package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.model.CourseObject;
import ukma.springboot.nextskill.repositories.CourseRepository;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.List;
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
