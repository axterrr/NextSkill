package ukma.springboot.nextskill.course;

import ukma.springboot.nextskill.common.models.responses.CourseResponse;
import ukma.springboot.nextskill.common.models.views.CourseView;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseResponse> getAll();
    CourseResponse get(UUID id);
    CourseResponse create(CourseView courseView);
    void enrollStudent(UUID courseId, UUID studentId);
    void unrollStudent(UUID courseUuid, UUID studentUuid);
    void delete(UUID id);
    CourseResponse getWithSectionsWithPostsAndTests(UUID id);
    CourseResponse getWithUsers(UUID id);
    boolean hasOwnerRights(UUID userUuid, UUID courseUuid);
    boolean isEnrolled(UUID courseUuid, UUID studentUuid);
    CourseResponse update(CourseView courseView);
    List<CourseResponse> getAllWithUsers();

}
