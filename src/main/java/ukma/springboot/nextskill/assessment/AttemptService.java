package ukma.springboot.nextskill.assessment;

import ukma.springboot.nextskill.common.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.common.models.responses.UserResponse;
import ukma.springboot.nextskill.common.models.views.TestAttemptView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AttemptService {
    List<TestAttemptResponse> getAll();
    TestAttemptResponse get(UUID id);
    TestAttemptResponse create(TestAttemptView view);
    TestAttemptResponse update(TestAttemptView view);
    void removeAllWithTest(UUID uuid);
    void delete(UUID id);

    Optional<TestAttemptResponse> getUnfinishedAttempt(UUID testId, UUID userID);
    List<TestAttemptResponse> getFinishedAttempts(UUID testId, UUID userId);
    TestAttemptResponse createNewAttempt(UUID testId, UUID userId);
    void checkAttemptAccess(UUID attemptId, UserResponse authenticated);
    void submitAttempt(UUID attemptId);

    void submitAttemptWithAnswers(UUID testId, UUID attemptId, Map<String, String> formData, UserResponse authenticated);
    boolean saveAttemptWithAnswers(UUID testId, UUID attemptId, Map<String, String> formData, UserResponse authenticated);
}
