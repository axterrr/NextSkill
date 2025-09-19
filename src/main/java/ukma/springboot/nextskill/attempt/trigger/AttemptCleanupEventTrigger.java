package ukma.springboot.nextskill.attempt.trigger;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.assessment.service.AttemptService;
import ukma.springboot.nextskill.option.OptionDeletedEvent;
import ukma.springboot.nextskill.question.QuestionDeletedEvent;

@Component
@AllArgsConstructor
public class AttemptCleanupEventTrigger {

    private final AttemptService attemptService;

    @EventListener
    public void handleOptionDeletedEvent(OptionDeletedEvent event) {
        attemptService.removeAllWithTest(event.getTestId());
    }

    @EventListener
    public void handleQuestionDeletedEvent(QuestionDeletedEvent event) {
        attemptService.removeAllWithTest(event.getTestId());
    }
}
