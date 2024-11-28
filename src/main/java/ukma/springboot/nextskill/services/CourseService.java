package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import java.util.List;
import java.util.UUID;

public interface CourseService extends GenericService<CourseView, CourseResponse> {
    List<CourseResponse> getCoursesWhereStudent(UUID studentId);
    List<CourseResponse> getCoursesWhereTeacher(UUID teacherId);
    void enrollStudent(UUID courseId, UUID studentId);
    CourseResponse getWithSectionsWithPostsAndTests(UUID id);
    CourseResponse getWithUsers(UUID id);
    List<CourseResponse> getAllWithUsers();
    boolean hasOwnerRights(UUID userUuid, UUID courseUuid);


    boolean isEnrolled(UUID courseUuid, UUID studentUuid);

    void unrollStudent(UUID courseUuid, UUID studentUuid);
}
