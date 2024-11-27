package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.QuestionEntity;
import ukma.springboot.nextskill.models.mappers.QuestionMapper;
import ukma.springboot.nextskill.models.responses.QuestionResponse;
import ukma.springboot.nextskill.models.views.QuestionView;
import ukma.springboot.nextskill.repositories.QuestionRepository;
import ukma.springboot.nextskill.services.QuestionService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public List<QuestionResponse> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::toQuestionResponse)
                .toList();
    }

    @Override
    public QuestionResponse get(UUID id) {
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", id));
        return QuestionMapper.toQuestionResponse(questionEntity);
    }

    @Override
    public QuestionResponse create(QuestionView view) {
        QuestionEntity questionEntity = questionRepository.save(
                QuestionMapper.toQuestionEntity(view)
        );
        return QuestionMapper.toQuestionResponse(questionEntity);
    }

    @Override
    public QuestionResponse update(QuestionView view) {
        QuestionEntity existingEntity = questionRepository.findById(view.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", view.getId()));
        QuestionEntity updatedEntity = questionRepository.save(
                QuestionMapper.mergeData(view, existingEntity)
        );
        return QuestionMapper.toQuestionResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", id));
        questionRepository.deleteById(id);
    }

    @Override
    public List<QuestionResponse> getTestQuestions(UUID testId) {
        return questionRepository.findByTestUuid(testId)
                .stream()
                .map(question -> {
                    question.getQuestionOptions().size();
                    return QuestionMapper.toQuestionResponse(question);
                })
                .toList();
    }
}
