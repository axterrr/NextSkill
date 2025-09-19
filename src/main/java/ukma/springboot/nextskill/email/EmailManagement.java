package ukma.springboot.nextskill.email;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import ukma.springboot.nextskill.email.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailManagement {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener
    void on(EmailSendEvent event) {
        emailService.sendEmail(event.getRecipient(), event.getSubject(), event.getText());
    }
}
