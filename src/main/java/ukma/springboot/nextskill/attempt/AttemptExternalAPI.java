package ukma.springboot.nextskill.attempt;

import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;

import java.util.UUID;

public interface AttemptExternalAPI {
    TestAttemptResponse create(TestAttemptView view);
    TestAttemptResponse update(TestAttemptView view);
    void delete(UUID id);
}
