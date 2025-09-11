package ukma.springboot.nextskill.test;

import ukma.springboot.nextskill.models.responses.TestResponse;

import java.util.List;
import java.util.UUID;

public interface TestInternalAPI {
    List<TestResponse> getAll();
    TestResponse get(UUID id);
}
