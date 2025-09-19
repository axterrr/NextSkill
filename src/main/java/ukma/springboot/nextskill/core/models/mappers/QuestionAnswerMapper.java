package ukma.springboot.nextskill.core.models.mappers;

import ukma.springboot.nextskill.core.models.entities.QuestionAnswerEntity;
import ukma.springboot.nextskill.core.models.entities.QuestionEntity;
import ukma.springboot.nextskill.core.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.core.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.core.models.responses.QuestionAnswerResponse;
import ukma.springboot.nextskill.core.models.views.QuestionAnswerView;

public class QuestionAnswerMapper {

    private QuestionAnswerMapper() {
    }

    public static QuestionAnswerResponse toQuestionAnswerResponse(QuestionAnswerEntity answerEntity) {
        return QuestionAnswerResponse.builder()
                .id(answerEntity.getId())
                .question(QuestionMapper.toQuestionResponse(answerEntity.getQuestion()))
                .answerOption(QuestionOptionMapper.toQuestionOptionResponse(answerEntity.getAnswerOption()))
                .build();
    }

    public static QuestionAnswerEntity toQuestionAnswerEntity(QuestionAnswerView view) {
        return QuestionAnswerEntity.builder()
                .id(view.getId())
                .testAttempt(TestAttemptEntity.builder().uuid(view.getTestAttemptId()).build())
                .question(QuestionEntity.builder().id(view.getQuestionId()).build())
                .answerOption(QuestionOptionEntity.builder().id(view.getQuestionOptionId()).build())
                .build();
    }

    public static QuestionAnswerEntity mergeData(QuestionAnswerView view, QuestionAnswerEntity entity) {
        return QuestionAnswerEntity.builder()
                .id(entity.getId())
                .testAttempt(entity.getTestAttempt())
                .answerOption(MapperUtility.orElse(
                        QuestionOptionEntity.builder().id(view.getQuestionOptionId()).build(),
                        entity.getAnswerOption()
                ))
                .question(entity.getQuestion())
                .build();
    }
}
