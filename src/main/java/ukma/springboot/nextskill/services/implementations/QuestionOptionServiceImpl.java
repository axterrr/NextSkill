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

    private static final String QUESTION_OPTION = "QuestionOption";
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
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, id));
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
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, view.getId()));
        QuestionOptionEntity updatedEntity = questionOptionRepository.save(
                QuestionOptionMapper.mergeData(view, existingEntity)
        );
        return QuestionOptionMapper.toQuestionOptionResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        if (questionOptionRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(QUESTION_OPTION, id);
        }
        questionOptionRepository.deleteById(id);
    }

    @Override
    public void setNewCorrect(UUID questionId, UUID optionId) {
        QuestionOptionEntity questionOptionEntity = questionOptionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, optionId));

        List<QuestionOptionEntity> allQuestionOptions =
                questionOptionRepository.getQuestionOptionEntitiesByQuestionId(questionId);

        for (QuestionOptionEntity option : allQuestionOptions) {
            option.setCorrect(false);
            questionOptionRepository.save(option);
        }

        questionOptionEntity.setCorrect(true);
        questionOptionRepository.save(questionOptionEntity);
    }
}
