package ukma.springboot.nextskill.assessment;

import ukma.springboot.nextskill.common.models.responses.TestResponse;
import ukma.springboot.nextskill.common.models.responses.UserResponse;
import ukma.springboot.nextskill.common.models.views.TestView;

import java.util.List;
import java.util.UUID;

public interface TestService {
    List<TestResponse> getAll();
    TestResponse get(UUID id);
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
