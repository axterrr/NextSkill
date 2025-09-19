package ukma.springboot.nextskill.course.handler;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.course.SectionCreateEvent;
import ukma.springboot.nextskill.course.SectionService;

@Component
@AllArgsConstructor
public class SectionCreateEventHandler {

    private final SectionService sectionExternalAPI;

    @EventListener
    public void handleSectionCreateEvent(SectionCreateEvent event) {
        this.sectionExternalAPI.create(event.getSectionView());
    }
}
