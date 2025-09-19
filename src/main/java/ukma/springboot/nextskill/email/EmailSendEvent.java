package ukma.springboot.nextskill.email;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailSendEvent extends ApplicationEvent {
    private final String recipient;
    private final String subject;
    private final String text;

    public EmailSendEvent(Object source, String recipient, String subject, String text) {
        super(source);
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
    }
}
