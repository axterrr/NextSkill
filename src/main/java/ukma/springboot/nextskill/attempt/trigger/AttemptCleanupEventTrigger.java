package ukma.springboot.nextskill.attempt.trigger;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.attempt.AttemptExternalAPI;
import ukma.springboot.nextskill.option.OptionChangedEvent;

@Component
@AllArgsConstructor
public class AttemptCleanupEventTrigger {

    private final AttemptExternalAPI attemptExternalAPI;

    @EventListener
    public void handleOptionChangedEvent(OptionChangedEvent event) {
        attemptExternalAPI.removeAllWithTest(event.getTestId());
    }
}
