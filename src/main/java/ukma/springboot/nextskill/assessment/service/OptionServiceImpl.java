package ukma.springboot.nextskill.assessment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.common.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.mappers.QuestionOptionMapper;
import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;
import ukma.springboot.nextskill.assessment.OptionService;
import ukma.springboot.nextskill.assessment.repository.OptionRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OptionServiceImpl implements OptionService {

    private static final String QUESTION_OPTION = "QuestionOption";
    private final OptionRepository optionRepository;

    @Override
    public List<QuestionOptionResponse> getAll() {
        return optionRepository.findAll()
            .stream()
            .map(QuestionOptionMapper::toQuestionOptionResponse)
            .toList();
    }

    @Override
    public QuestionOptionResponse get(UUID id) {
        QuestionOptionEntity questionOptionEntity = optionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, id));
        return QuestionOptionMapper.toQuestionOptionResponse(questionOptionEntity);
    }

    @Override
    public QuestionOptionResponse create(QuestionOptionView view) {
        QuestionOptionEntity questionOptionEntity = optionRepository.save(
                QuestionOptionMapper.toQuestionOptionEntity(view)
        );
        return QuestionOptionMapper.toQuestionOptionResponse(questionOptionEntity);
    }

    @Override
    public QuestionOptionResponse update(QuestionOptionView view) {
        QuestionOptionEntity existingEntity = optionRepository.findById(view.getId())
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, view.getId()));
        QuestionOptionEntity updatedEntity = optionRepository.save(
                QuestionOptionMapper.mergeData(view, existingEntity)
        );
        return QuestionOptionMapper.toQuestionOptionResponse(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        if (optionRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(QUESTION_OPTION, id);
        }
        optionRepository.deleteById(id);
    }

    @Override
    public void setNewCorrect(UUID questionId, UUID optionId) {
        QuestionOptionEntity questionOptionEntity = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_OPTION, optionId));

        List<QuestionOptionEntity> allQuestionOptions =
                optionRepository.getQuestionOptionEntitiesByQuestionId(questionId);

        for (QuestionOptionEntity option : allQuestionOptions) {
            option.setCorrect(false);
            optionRepository.save(option);
        }

        questionOptionEntity.setCorrect(true);
        optionRepository.save(questionOptionEntity);
    }
}
