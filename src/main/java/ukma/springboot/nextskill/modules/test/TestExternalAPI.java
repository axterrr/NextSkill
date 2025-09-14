package ukma.springboot.nextskill.modules.test;

import org.springframework.modulith.NamedInterface;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.TestView;

import java.util.UUID;

@NamedInterface
public interface TestExternalAPI {
    TestEntity get(UUID id);
    TestResponse create(TestView view);
    TestResponse update(TestView view);
    void delete(UUID id);

    boolean hasOwnerRights(UUID userId, UUID testId);
    void checkTestAccess(UUID testUuid, UserResponse user);
    TestResponse getTestByAttempt(UUID attemptId);
    void unhide(UUID testId);
    void hide(UUID testId);
    TestResponse getTestByQuestion(UUID questionId);
}
