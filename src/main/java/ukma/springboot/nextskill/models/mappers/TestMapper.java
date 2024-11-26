package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.responses.TestResponse;

public class TestMapper {

    private TestMapper() {}

    public static TestResponse toTestResponse(TestEntity testEntity) {
        return TestResponse.builder()
                .uuid(testEntity.getUuid())
                .name(testEntity.getName())
                .description(testEntity.getDescription())
                .questions(MapperUtility.mapIfInitialized(testEntity.getQuestions(), QuestionMapper::toQuestionResponse))
                .attempts(MapperUtility.mapIfInitialized(testEntity.getAttempts(), TestAttemptMapper::toTestAttemptResponse))
                .build();
    }
}
