package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;

public class QuestionOptionMapper {

    private QuestionOptionMapper() {}

    public static QuestionOptionResponse toQuestionOptionResponse(QuestionOptionEntity optionEntity) {
        return QuestionOptionResponse.builder()
                .id(optionEntity.getId())
                .optionText(optionEntity.getOptionText())
                .isCorrect(optionEntity.isCorrect())
                .build();
    }
}
