package ukma.springboot.nextskill.core.services;

import ukma.springboot.nextskill.core.models.responses.TestResponse;
import ukma.springboot.nextskill.core.models.responses.UserResponse;
import ukma.springboot.nextskill.core.models.views.TestView;

import java.util.UUID;

public interface TestService extends GenericService<TestView, TestResponse> {
    boolean hasOwnerRights(UUID userId, UUID testId);
    void checkTestAccess(UUID testUuid, UserResponse user);
    TestResponse getTestByAttempt(UUID attemptId);
    void unhide(UUID testId);
    void hide(UUID testId);
    TestResponse getTestByQuestion(UUID questionId);
}
