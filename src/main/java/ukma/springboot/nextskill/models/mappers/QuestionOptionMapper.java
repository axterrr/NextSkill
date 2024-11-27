package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.QuestionEntity;
import ukma.springboot.nextskill.models.entities.QuestionOptionEntity;
import ukma.springboot.nextskill.models.responses.QuestionOptionResponse;
import ukma.springboot.nextskill.models.views.QuestionOptionView;

public class QuestionOptionMapper {

    private QuestionOptionMapper() {
    }

    public static QuestionOptionResponse toQuestionOptionResponse(QuestionOptionEntity optionEntity) {
        return QuestionOptionResponse.builder()
                .id(optionEntity.getId())
                .optionText(optionEntity.getOptionText())
                .isCorrect(optionEntity.isCorrect())
                .build();
    }

    public static QuestionOptionEntity toQuestionOptionEntity(QuestionOptionView view) {
        return QuestionOptionEntity.builder()
                .question(QuestionEntity.builder().id(view.getQuestionId()).build())
                .id(view.getId())
                .isCorrect(view.isCorrect())
                .optionText(view.getOptionText())
                .build();
    }

    public static QuestionOptionEntity mergeData(QuestionOptionView view, QuestionOptionEntity entity) {
        return QuestionOptionEntity.builder()
                .id(entity.getId())
                .optionText(MapperUtility.orElse(view.getOptionText(), entity.getOptionText()))
                .isCorrect(MapperUtility.orElse(view.isCorrect(), entity.isCorrect()))
                .question(entity.getQuestion())
                .build();
    }
}
