package ukma.springboot.nextskill.assessment.service;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.assessment.AnswerService;
import ukma.springboot.nextskill.assessment.AttemptService;
import ukma.springboot.nextskill.common.exceptions.NoAccessException;
import ukma.springboot.nextskill.common.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.TestAttemptMapper;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;
import ukma.springboot.nextskill.assessment.repository.AttemptRepository;
import ukma.springboot.nextskill.assessment.TestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private static final String TEST_ATTEMPT = "TestAttempt";
    private final AttemptRepository attemptRepository;
    private final TestService testService;
    private final AnswerService answerService;

    @Override
    public List<TestAttemptResponse> getAll() {
        return attemptRepository.findAll()
            .stream()
            .map(TestAttemptMapper::toTestAttemptResponse)
            .toList();
    }

    @Override
    public TestAttemptResponse get(UUID id) {
        TestAttemptEntity testAttemptEntity = attemptRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, id));
        Hibernate.initialize(testAttemptEntity.getAnswers());
        return TestAttemptMapper.toTestAttemptResponse(testAttemptEntity);
    }


    @Override
    public TestAttemptResponse create(TestAttemptView view) {
        TestAttemptEntity testAttemptEntity = attemptRepository.save(
                TestAttemptMapper.toTestAttemptEntity(view)
        );
        return TestAttemptMapper.toTestAttemptResponse(testAttemptEntity);
    }

    @Override
    public TestAttemptResponse update(TestAttemptView view) {
        TestAttemptEntity existingEntity = attemptRepository.findById(view.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, view.getUuid()));
        TestAttemptEntity updatedEntity = attemptRepository.save(
                TestAttemptMapper.mergeData(view, existingEntity)
        );
        return TestAttemptMapper.toTestAttemptResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        if (attemptRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(TEST_ATTEMPT, id);
        }
        attemptRepository.deleteById(id);
    }

    @Override
    public Optional<TestAttemptResponse> getUnfinishedAttempt(UUID testId, UUID userID) {
        return attemptRepository.findTestAttemptEntityByCompletedByUuidAndSubmittedFalseAndTest_Uuid(userID, testId)
                .map(TestAttemptMapper::toTestAttemptResponse);
    }


    @Override
    public List<TestAttemptResponse> getFinishedAttempts(UUID testId, UUID userId) {
        return attemptRepository.findTestAttemptEntitiesByCompletedByUuidAndSubmittedTrueAndTest_Uuid(userId, testId)
                .stream()
                .map(TestAttemptMapper::toTestAttemptResponse)
                .toList();
    }

    @Override
    public TestAttemptResponse createNewAttempt(UUID testId, UUID userId) {
        TestResponse test = testService.get(testId);
        CourseResponse courseEntity = test.getSection().getCourse();

        if (test.isHidden() && !(courseEntity.getTeacher().getUuid().equals(userId)))
            throw new NoAccessException("This user has no access to the test");

        TestAttemptEntity newAttempt = TestAttemptEntity.builder()
                .startTime(LocalDateTime.now())
                .submitted(false)
                .completedBy(UserEntity.builder().uuid(userId).build())
                .test(TestEntity.builder().uuid(testId).build())
                .build();

        TestAttemptEntity savedAttempt = attemptRepository.save(newAttempt);

        return TestAttemptMapper.toTestAttemptResponse(savedAttempt);
    }

    @Override
    public void checkAttemptAccess(UUID attemptId, UserResponse authenticated) {
        TestAttemptEntity testAttemptEntity = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException(TEST_ATTEMPT, attemptId));
        UUID userId = authenticated.getUuid();

        if (!testAttemptEntity.getCompletedBy().getUuid().equals(userId)) {
            throw new NoAccessException("This user has no access to this attempt");
        }
    }

    @Override
    public void submitAttempt(UUID attemptId) {
        Optional<TestAttemptEntity> attemptOptional = attemptRepository.findById(attemptId);

        if (attemptOptional.isEmpty()) {
            throw new ResourceNotFoundException(TEST_ATTEMPT, attemptId);
        }

        TestAttemptEntity attempt = attemptOptional.get();

        if (attempt.isSubmitted()) {
            throw new IllegalStateException("Attempt has already been submitted.");
        }

        attempt.setSubmitted(true);
        attempt.setEndTime(LocalDateTime.now());

        attemptRepository.save(attempt);
    }

    @Override
    public void removeAllWithTest(UUID uuid) {
        attemptRepository.deleteAllByTestUuid(uuid);
    }

    @Override
    public void submitAttemptWithAnswers(UUID testId, UUID attemptId, Map<String, String> formData, UserResponse authenticated) {
        TestAttemptResponse attempt = this.get(attemptId);
        if (attempt.isSubmitted()) {
            return;
        }

        testService.checkTestAccess(testId, authenticated);
        this.checkAttemptAccess(attemptId, authenticated);

        answerService.updateSavedAnswers(formData, attemptId);
        this.submitAttempt(attemptId);
    }

    @Override
    public boolean saveAttemptWithAnswers(UUID testId, UUID attemptId, Map<String, String> formData, UserResponse authenticated) {
        TestAttemptResponse attempt = this.get(attemptId);
        if (attempt.isSubmitted()) {
            return false;
        }

        testService.checkTestAccess(testId, authenticated);
        this.checkAttemptAccess(attemptId, authenticated);

        answerService.updateSavedAnswers(formData, attemptId);
        return true;
    }
}
