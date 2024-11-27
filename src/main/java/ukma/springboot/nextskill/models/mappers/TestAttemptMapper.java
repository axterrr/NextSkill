package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.views.TestAttemptView;
import ukma.springboot.nextskill.models.views.TestView;

public class TestAttemptMapper {

    private TestAttemptMapper() {}

    public static TestAttemptResponse toTestAttemptResponse(TestAttemptEntity attemptEntity) {
        return TestAttemptResponse.builder()
                .uuid(attemptEntity.getUuid())
                .startTime(attemptEntity.getStartTime())
                .endTime(attemptEntity.getEndTime())
                .submitted(attemptEntity.isSubmitted())
                .completedBy(UserMapper.toUserResponse(attemptEntity.getCompletedBy()))
                .answers(MapperUtility.mapIfInitialized(attemptEntity.getAnswers(), QuestionAnswerMapper::toQuestionAnswerResponse))
                .build();
    }

    public static TestAttemptEntity toTestEntity(TestAttemptView view) {
        return TestAttemptEntity.builder()
                .uuid(view.getUuid())
                .startTime(view.getStartTime())
                .endTime(view.getEndTime())
                .submitted(view.isSubmitted())
                .completedBy(UserEntity.builder().uuid(view.getCompletedById()).build())
                .test(TestEntity.builder().uuid(view.getTestId()).build())
                .build();
    }

    public static TestAttemptEntity mergeData(TestAttemptView view, TestAttemptEntity entity) {
        return TestAttemptEntity.builder()
                .uuid(entity.getUuid())
                .startTime(entity.getStartTime())
                .endTime(MapperUtility.orElse(view.getEndTime(), entity.getEndTime()))
                .submitted(MapperUtility.orElse(view.isSubmitted(), entity.isSubmitted()))
                .completedBy(entity.getCompletedBy())
                .test(entity.getTest())
                .build();
    }
}
