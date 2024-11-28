package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.NoAccessException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.mappers.TestMapper;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.TestView;
import ukma.springboot.nextskill.repositories.TestRepository;
import ukma.springboot.nextskill.services.TestService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;

    @Override
    public List<TestResponse> getAll() {
        return testRepository.findAll().stream().map(TestMapper::toTestResponse).toList();
    }

    @Override
    public TestResponse get(UUID id) {
        TestEntity test = testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", id));
        Hibernate.initialize(test.getQuestions());
        Hibernate.initialize(test.getAttempts());
        return TestMapper.toTestResponse(test);
    }

    @Override
    public TestResponse create(TestView view) {
        TestEntity testEntity = testRepository.save(TestMapper.toTestEntity(view));
        return TestMapper.toTestResponse(testEntity);
    }

    @Override
    public TestResponse update(TestView view) {
        TestEntity existingTest = testRepository.findById(view.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Test", view.getUuid()));
        TestEntity testEntity = testRepository.save(TestMapper.mergeData(view, existingTest));
        return TestMapper.toTestResponse(testEntity);
    }

    @Override
    public void delete(UUID id) {
        if (testRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Test", id);
        }
        testRepository.deleteById(id);
    }

    @Override
    public boolean hasOwnerRights(UUID userId, UUID testId) {
        TestEntity test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("Test", testId));
        UUID courseOwner = test.getSection().getCourse().getTeacher().getUuid();
        return courseOwner.equals(userId);
    }

    @Override
    public void checkTestAccess(UUID testUuid, UserResponse user) {
        UUID userId = user.getUuid();
        TestEntity test = testRepository.findById(testUuid).orElseThrow(() -> new ResourceNotFoundException("Test", testUuid));
        CourseEntity course = test.getSection().getCourse();

        boolean isOwner = course.getTeacher().getUuid().equals(userId);

        if (!isOwner && course.getStudents().stream().noneMatch(stud -> stud.getUuid().equals(userId))) {
            throw new NoAccessException("This user has no access to this test");
        }
    }

    @Override
    public TestResponse getTestByAttempt(UUID attemptId) {
        TestEntity test = testRepository.findTestEntityByAttemptsUuid(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Test with attempt", attemptId));
        test.getQuestions().size();
        test.getQuestions().size();
        return TestMapper.toTestResponse(test);
    }

    @Override
    public void hide(UUID testId) {
        TestEntity test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", testId));
        test.setHidden(true);
        testRepository.save(test);
    }

    @Override
    public TestResponse getTestByQuestion(UUID questionId) {
        TestEntity test = testRepository.findByQuestionsId(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Test with question", questionId));
        return TestMapper.toTestResponse(test);
    }

    @Override
    public void unhide(UUID testId) {
        TestEntity test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", testId));
        test.setHidden(false);
        testRepository.save(test);
    }
}
