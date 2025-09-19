package ukma.springboot.nextskill.assessment.handler;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.assessment.AttemptService;
import ukma.springboot.nextskill.assessment.OptionDeletedEvent;
import ukma.springboot.nextskill.assessment.QuestionDeletedEvent;

@Component
@AllArgsConstructor
public class AttemptCleanupEventHandler {

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
