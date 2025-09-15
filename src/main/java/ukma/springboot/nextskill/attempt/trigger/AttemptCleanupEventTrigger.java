package ukma.springboot.nextskill.attempt.trigger;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.attempt.AttemptExternalAPI;
import ukma.springboot.nextskill.option.OptionDeletedEvent;
import ukma.springboot.nextskill.question.QuestionDeletedEvent;

@Component
@AllArgsConstructor
public class AttemptCleanupEventTrigger {

    private final AttemptExternalAPI attemptExternalAPI;

    @EventListener
    public void handleOptionDeletedEvent(OptionDeletedEvent event) {
        attemptExternalAPI.removeAllWithTest(event.getTestId());
    }

    @EventListener
    public void handleQuestionDeletedEvent(QuestionDeletedEvent event) {
        attemptExternalAPI.removeAllWithTest(event.getTestId());
    }
}
