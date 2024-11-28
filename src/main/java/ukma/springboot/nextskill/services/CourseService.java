package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import java.util.List;
import java.util.UUID;

public interface CourseService extends GenericService<CourseView, CourseResponse> {
    void enrollStudent(UUID courseId, UUID studentId);
    CourseResponse getWithSectionsWithPostsAndTests(UUID id);
    CourseResponse getWithUsers(UUID id);
    List<CourseResponse> getAllWithUsers();
    Object isEnrolled(UUID courseUuid, UUID studentUuid);
}
