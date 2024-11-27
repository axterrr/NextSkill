package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestAttemptService extends GenericService<TestAttemptView, TestAttemptResponse> {
    Optional<TestAttemptResponse> getUnfinishedAttempt(UUID testId, UUID userID);
    List<TestAttemptResponse> getFinishedAttempts(UUID testId, UUID userId);
    TestAttemptResponse createNewAttempt(UUID testId, UUID userId);
}
