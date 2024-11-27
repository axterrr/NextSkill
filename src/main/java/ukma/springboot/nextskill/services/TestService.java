package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.TestView;

import java.util.UUID;

public interface TestService extends GenericService<TestView, TestResponse> {
    boolean hasOwnerRights(UUID userId, UUID testId);
    void checkTestAccess(UUID testUuid, UserResponse user);
}
