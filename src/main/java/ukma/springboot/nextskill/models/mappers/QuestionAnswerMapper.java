package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.QuestionAnswerEntity;
import ukma.springboot.nextskill.models.responses.QuestionAnswerResponse;

public class QuestionAnswerMapper {

    private QuestionAnswerMapper() {}

    public static QuestionAnswerResponse toQuestionAnswerResponse(QuestionAnswerEntity answerEntity) {
        return QuestionAnswerResponse.builder()
                .id(answerEntity.getId())
                .question(QuestionMapper.toQuestionResponse(answerEntity.getQuestion()))
                .answerOption(QuestionOptionMapper.toQuestionOptionResponse(answerEntity.getAnswerOption()))
                .build();
    }
}
