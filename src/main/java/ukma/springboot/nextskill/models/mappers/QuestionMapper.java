package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.QuestionEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.responses.QuestionResponse;
import ukma.springboot.nextskill.models.views.QuestionView;
import ukma.springboot.nextskill.models.views.TestView;

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

    public static QuestionEntity toQuestionEntity(QuestionView view) {
        return QuestionEntity.builder()
                .id(view.getId())
                .questionText(view.getQuestionText())
                .test(TestEntity.builder().uuid(view.getTestId()).build())
                .build();
    }

    public static QuestionEntity mergeData(QuestionView view, QuestionEntity entity) {
        return QuestionEntity.builder()
                .id(entity.getId())
                .questionText(MapperUtility.orElse(view.getQuestionText(), entity.getQuestionText()))
                .test(entity.getTest())
                .questionOptions(entity.getQuestionOptions())
                .build();
    }
}
