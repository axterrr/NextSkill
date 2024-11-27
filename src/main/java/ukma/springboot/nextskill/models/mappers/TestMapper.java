package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.views.TestView;

public class TestMapper {

    private TestMapper() {}

    public static TestResponse toTestResponse(TestEntity testEntity) {
        if (testEntity == null) { return null; }
        return TestResponse.builder()
                .uuid(testEntity.getUuid())
                .name(testEntity.getName())
                .description(testEntity.getDescription())
                .createdAt(testEntity.getCreatedAt())
                .isHidden(testEntity.isHidden())
                .section(SectionMapper.toSectionResponse(testEntity.getSection()))
                .questions(MapperUtility.mapIfInitialized(testEntity.getQuestions(), QuestionMapper::toQuestionResponse))
                .attempts(MapperUtility.mapIfInitialized(testEntity.getAttempts(), TestAttemptMapper::toTestAttemptResponse))
                .build();
    }

    public static TestResponse toTestResponseWithoutSection(TestEntity testEntity) {
        if (testEntity == null) { return null; }
        return TestResponse.builder()
                .uuid(testEntity.getUuid())
                .name(testEntity.getName())
                .description(testEntity.getDescription())
                .createdAt(testEntity.getCreatedAt())
                .isHidden(testEntity.isHidden())
                .questions(MapperUtility.mapIfInitialized(testEntity.getQuestions(), QuestionMapper::toQuestionResponse))
                .attempts(MapperUtility.mapIfInitialized(testEntity.getAttempts(), TestAttemptMapper::toTestAttemptResponse))
                .build();
    }

    public static TestEntity toTestEntity(TestView testView) {
        return TestEntity.builder()
                .uuid(testView.getUuid())
                .isHidden(testView.isHidden())
                .name(testView.getName())
                .description(testView.getDescription())
                .section(SectionEntity.builder().uuid(testView.getSectionId()).build())
                .build();
    }

    public static TestEntity mergeData(TestView view, TestEntity entity) {
        return TestEntity.builder()
                .uuid(entity.getUuid())
                .name(MapperUtility.orElse(view.getName(), entity.getName()))
                .description(MapperUtility.orElse(view.getDescription(), entity.getDescription()))
                .isHidden(MapperUtility.orElse(view.isHidden(), entity.isHidden()))
                .section(entity.getSection())
                .build();
    }
}
