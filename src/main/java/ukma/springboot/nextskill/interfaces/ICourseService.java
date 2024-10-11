package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.CourseObject;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    Course getCourse(UUID id);
    List<Course> getAllCourses();
    Course createCourse(Course course);
    Course updateCourse(UUID id, Course updatedCourse);
    void deleteCourse(UUID id);

    CourseObject addCourseItem(CourseObject courseObject);
    List<CourseObject> getAllCourseItems();
    CourseObject getCourseItemById(UUID courseObjectId);
    void removeCourseItem(UUID courseObjectId);
}
