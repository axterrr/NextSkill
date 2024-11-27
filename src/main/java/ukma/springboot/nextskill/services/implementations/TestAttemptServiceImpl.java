package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.mappers.TestAttemptMapper;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;
import ukma.springboot.nextskill.repositories.TestAttemptRepository;
import ukma.springboot.nextskill.services.TestAttemptService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestAttemptServiceImpl implements TestAttemptService {

    private final TestAttemptRepository testAttemptRepository;

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
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", id));
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
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", view.getUuid()));
        TestAttemptEntity updatedEntity = testAttemptRepository.save(
                TestAttemptMapper.mergeData(view, existingEntity)
        );
        return TestAttemptMapper.toTestAttemptResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        testAttemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt", id));
        testAttemptRepository.deleteById(id);
    }
}
