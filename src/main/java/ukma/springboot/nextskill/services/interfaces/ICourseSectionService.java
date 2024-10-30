package ukma.springboot.nextskill.services.interfaces;

import ukma.springboot.nextskill.model.pojo.CourseSection;

import java.util.List;
import java.util.UUID;

public interface ICourseSectionService {
    CourseSection getCourseSection(UUID id);
    List<CourseSection> getAllCourseSections();
    CourseSection createCourseSection(CourseSection section);
    CourseSection updateCourseSection(UUID id, CourseSection updatedCourseSection);
    void deleteCourseSection(UUID id);
}
