package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.mappers.QuestionOptionMapper;
import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;
import ukma.springboot.nextskill.repositories.QuestionOptionRepository;
import ukma.springboot.nextskill.services.QuestionOptionService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionOptionServiceImpl implements QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;

    @Override
    public List<QuestionOptionResponse> getAll() {
        return questionOptionRepository.findAll()
                .stream()
                .map(QuestionOptionMapper::toQuestionOptionResponse)
                .toList();
    }

    @Override
    public QuestionOptionResponse get(UUID id) {
        QuestionOptionEntity questionOptionEntity = questionOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionOption", id));
        return QuestionOptionMapper.toQuestionOptionResponse(questionOptionEntity);
    }

    @Override
    public QuestionOptionResponse create(QuestionOptionView view) {
        QuestionOptionEntity questionOptionEntity = questionOptionRepository.save(
                QuestionOptionMapper.toQuestionOptionEntity(view)
        );
        return QuestionOptionMapper.toQuestionOptionResponse(questionOptionEntity);
    }

    @Override
    public QuestionOptionResponse update(QuestionOptionView view) {
        QuestionOptionEntity existingEntity = questionOptionRepository.findById(view.getId())
                .orElseThrow(() -> new ResourceNotFoundException("QuestionOption", view.getId()));
        QuestionOptionEntity updatedEntity = questionOptionRepository.save(
                QuestionOptionMapper.mergeData(view, existingEntity)
        );
        return QuestionOptionMapper.toQuestionOptionResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        questionOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionOption", id));
        questionOptionRepository.deleteById(id);
    }
}
