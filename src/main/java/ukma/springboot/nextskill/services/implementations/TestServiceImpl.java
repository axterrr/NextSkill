package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.mappers.TestMapper;
import ukma.springboot.nextskill.models.responses.TestResponse;
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
        testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", id));
        testRepository.deleteById(id);
    }

    @Override
    public boolean hasOwnerRights(UUID userId, UUID testId) {
        TestEntity test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("Test", testId));
        UUID courseOwner = test.getSection().getCourse().getTeacher().getUuid();
        return courseOwner.equals(userId);
    }
}
