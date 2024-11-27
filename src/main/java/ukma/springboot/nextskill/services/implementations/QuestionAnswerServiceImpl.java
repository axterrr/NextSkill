package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.mappers.QuestionAnswerMapper;
import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.models.views.QuestionAnswerView;
import ukma.springboot.nextskill.repositories.QuestionAnswerRepository;
import ukma.springboot.nextskill.repositories.QuestionOptionRepository;
import ukma.springboot.nextskill.services.QuestionAnswerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private static final String QUESTION_ANSWER = "QuestionAnswer";
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionOptionRepository questionOptionRepository;

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
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_ANSWER, id));
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
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_ANSWER, view.getId()));
        QuestionAnswerEntity updatedEntity = questionAnswerRepository.save(
                QuestionAnswerMapper.mergeData(view, existingEntity)
        );
        return QuestionAnswerMapper.toQuestionAnswerResponse(updatedEntity);
    }



    @Override
    public void delete(UUID id) {
        if (questionAnswerRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(QUESTION_ANSWER, id);
        }
        questionAnswerRepository.deleteById(id);
    }

    @Override
    public void updateSavedAnswers(Map<String, String> map, UUID attemptId) {
        List<QuestionAnswerEntity> answers = questionAnswerRepository.findByTestAttemptUuid(attemptId);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String questionId = entry.getKey();
            String optionId = entry.getValue();

            Optional<QuestionAnswerEntity> existingAnswer = answers.stream()
                    .filter(answer -> answer.getQuestion().getId().toString().equals(questionId))
                    .findFirst();

            if (existingAnswer.isPresent()) {
                Optional<QuestionOptionEntity> option = questionOptionRepository.findById(UUID.fromString(optionId));

                if (option.isPresent()) {
                    QuestionAnswerEntity answerEntity = existingAnswer.get();
                    answerEntity.setAnswerOption(option.get());

                    questionAnswerRepository.save(answerEntity);
                }
            } else {
                QuestionAnswerView answer = QuestionAnswerView.builder()
                        .questionId(UUID.fromString(questionId))
                        .questionOptionId(UUID.fromString(optionId))
                        .testAttemptId(attemptId)
                        .build();

                this.create(answer);
            }
        }
    }
}
