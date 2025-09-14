package ukma.springboot.nextskill.modules.course;

import org.springframework.modulith.NamedInterface;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import java.util.List;
import java.util.UUID;

@NamedInterface
public interface CourseExternalAPI {
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
