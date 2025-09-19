package ukma.springboot.nextskill.email.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.email.EmailSendEvent;
import ukma.springboot.nextskill.email.EmailService;

@Service
@RequiredArgsConstructor
public class EmailSendEventHandler {

    private final EmailService emailService;

    @Async
    @EventListener
    void on(EmailSendEvent event) {
        emailService.sendEmail(event.getRecipient(), event.getSubject(), event.getText());
    }
}
