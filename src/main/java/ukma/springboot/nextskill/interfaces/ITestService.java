package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.Test;

import java.util.List;
import java.util.UUID;

public interface ITestService {
    Test createTest(Test test);
    Test updateTest(UUID id, Test test);
    List<Test> getAllTests();
    Test getTestById(UUID testId);
    void deleteTest(UUID testId);
    double evaluateTest(UUID testId, List<UUID> answerIds);
}