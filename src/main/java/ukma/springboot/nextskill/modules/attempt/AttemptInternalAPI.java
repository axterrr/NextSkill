package ukma.springboot.nextskill.modules.attempt;

import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttemptInternalAPI {
    List<TestAttemptResponse> getAll();
}
