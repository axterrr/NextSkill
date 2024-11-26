package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;

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
}
