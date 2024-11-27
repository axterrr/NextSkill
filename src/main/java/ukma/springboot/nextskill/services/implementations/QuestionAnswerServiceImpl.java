package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;
import ukma.springboot.nextskill.models.mappers.QuestionAnswerMapper;
import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.models.views.QuestionAnswerView;
import ukma.springboot.nextskill.repositories.QuestionAnswerRepository;
import ukma.springboot.nextskill.services.QuestionAnswerService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private final QuestionAnswerRepository questionAnswerRepository;

    @Override
    public List<QuestionAnswerResponse> getAll() {
        return questionAnswerRepository.findAll()
                .stream()
                .map(QuestionAnswerMapper::toQuestionAnswerResponse)
                .toList();
    }

    @Override
    public QuestionAnswerResponse get(UUID id) {
        QuestionAnswerEntity questionAnswerEntity = questionAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer", id));
        return QuestionAnswerMapper.toQuestionAnswerResponse(questionAnswerEntity);
    }

    @Override
    public QuestionAnswerResponse create(QuestionAnswerView view) {
        QuestionAnswerEntity questionAnswerEntity = questionAnswerRepository.save(
                QuestionAnswerMapper.toQuestionAnswerEntity(view)
        );
        return QuestionAnswerMapper.toQuestionAnswerResponse(questionAnswerEntity);
    }

    @Override
    public QuestionAnswerResponse update(QuestionAnswerView view) {
        QuestionAnswerEntity existingEntity = questionAnswerRepository.findById(view.getId())
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer", view.getId()));
        QuestionAnswerEntity updatedEntity = questionAnswerRepository.save(
                QuestionAnswerMapper.mergeData(view, existingEntity)
        );
        return QuestionAnswerMapper.toQuestionAnswerResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        questionAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer", id));
        questionAnswerRepository.deleteById(id);
    }
}
