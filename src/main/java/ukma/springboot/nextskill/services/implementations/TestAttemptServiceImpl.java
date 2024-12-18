package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.NoAccessException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.TestAttemptMapper;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;
import ukma.springboot.nextskill.repositories.TestAttemptRepository;
import ukma.springboot.nextskill.repositories.TestRepository;
import ukma.springboot.nextskill.services.TestAttemptService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestAttemptServiceImpl implements TestAttemptService {

    private static final String TEST_ATTEMPT = "TestAttempt";
    private final TestAttemptRepository testAttemptRepository;
    private final TestRepository testRepository;

    @Override
    public List<TestAttemptResponse> getAll() {
        return testAttemptRepository.findAll()
                .stream()
                .map(TestAttemptMapper::toTestAttemptResponse)
                .toList();
    }

    @Override
    public TestAttemptResponse get(UUID id) {
        TestAttemptEntity testAttemptEntity = testAttemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, id));
        Hibernate.initialize(testAttemptEntity.getAnswers());
        return TestAttemptMapper.toTestAttemptResponse(testAttemptEntity);
    }

    @Override
    public TestAttemptResponse create(TestAttemptView view) {
        TestAttemptEntity testAttemptEntity = testAttemptRepository.save(
                TestAttemptMapper.toTestAttemptEntity(view)
        );
        return TestAttemptMapper.toTestAttemptResponse(testAttemptEntity);
    }

    @Override
    public TestAttemptResponse update(TestAttemptView view) {
        TestAttemptEntity existingEntity = testAttemptRepository.findById(view.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, view.getUuid()));
        TestAttemptEntity updatedEntity = testAttemptRepository.save(
                TestAttemptMapper.mergeData(view, existingEntity)
        );
        return TestAttemptMapper.toTestAttemptResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        if (testAttemptRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(TEST_ATTEMPT, id);
        }
        testAttemptRepository.deleteById(id);
    }

    @Override
    public Optional<TestAttemptResponse> getUnfinishedAttempt(UUID testId, UUID userID) {
        return testAttemptRepository.findTestAttemptEntityByCompletedByUuidAndSubmittedFalseAndTest_Uuid(userID, testId)
                .map(TestAttemptMapper::toTestAttemptResponse);
    }


    @Override
    public List<TestAttemptResponse> getFinishedAttempts(UUID testId, UUID userId) {
        return testAttemptRepository.findTestAttemptEntitiesByCompletedByUuidAndSubmittedTrueAndTest_Uuid(userId, testId)
                .stream()
                .map(TestAttemptMapper::toTestAttemptResponse)
                .toList();
    }

    @Override
    public TestAttemptResponse createNewAttempt(UUID testId, UUID userId) {
        TestEntity test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", testId));

        CourseEntity courseEntity = test.getSection().getCourse();

        if (test.isHidden() && !(courseEntity.getTeacher().getUuid().equals(userId)))
            throw new NoAccessException("This user has no access to the test");

        TestAttemptEntity newAttempt = TestAttemptEntity.builder()
                .startTime(LocalDateTime.now())
                .submitted(false)
                .completedBy(UserEntity.builder().uuid(userId).build())
                .test(test)
                .build();

        TestAttemptEntity savedAttempt = testAttemptRepository.save(newAttempt);

        return TestAttemptMapper.toTestAttemptResponse(savedAttempt);
    }

    @Override
    public void checkAttemptAccess(UUID attemptId, UserResponse authenticated) {
        TestAttemptEntity testAttemptEntity = testAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, attemptId));
        UUID userId = authenticated.getUuid();

        if (!testAttemptEntity.getCompletedBy().getUuid().equals(userId)) {
            throw new NoAccessException("This user has no access to this attempt");
        }
    }

    @Override
    public void submitAttempt(UUID attemptId) {
        Optional<TestAttemptEntity> attemptOptional = testAttemptRepository.findById(attemptId);

        if (attemptOptional.isEmpty()) {
            throw new ResourceNotFoundException(TEST_ATTEMPT, attemptId);
        }

        TestAttemptEntity attempt = attemptOptional.get();

        if (attempt.isSubmitted()) {
            throw new IllegalStateException("Attempt has already been submitted.");
        }

        attempt.setSubmitted(true);
        attempt.setEndTime(LocalDateTime.now());

        testAttemptRepository.save(attempt);
    }

    @Override
    public void removeAllWithTest(UUID uuid) {
        testAttemptRepository.deleteAllByTestUuid(uuid);
    }
}
