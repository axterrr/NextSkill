package ukma.springboot.nextskill.assessment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class QuestionDeletedEvent extends ApplicationEvent {
    private final UUID testId;
    private final UUID questionId;

    public QuestionDeletedEvent(Object source, UUID testId, UUID questionId) {
        super(source);
        this.testId = testId;
        this.questionId = questionId;
    }
}
