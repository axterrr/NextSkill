package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import java.util.List;
import java.util.UUID;

public interface CourseService extends GenericService<CourseView, CourseResponse> {
    List<CourseResponse> getCoursesWhereStudent(UUID studentId);
    List<CourseResponse> getCoursesWhereTeacher(UUID teacherId);

    void enrollStudent(UUID courseId, UUID studentId);
}
