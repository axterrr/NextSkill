package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.QuestionEntity;
import ukma.springboot.nextskill.models.responses.QuestionResponse;

public class QuestionMapper {

    private QuestionMapper() {}

    public static QuestionResponse toQuestionResponse(QuestionEntity questionEntity) {
        return QuestionResponse.builder()
                .id(questionEntity.getId())
                .questionText(questionEntity.getQuestionText())
                .questionOptions(MapperUtility.mapIfInitialized(
                        questionEntity.getQuestionOptions(), QuestionOptionMapper::toQuestionOptionResponse
                ))
                .build();
    }
}
