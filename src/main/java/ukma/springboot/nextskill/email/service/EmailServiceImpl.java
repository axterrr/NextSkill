package ukma.springboot.nextskill.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.email.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${EMAIL_USERNAME}")
    private String sender;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(sender);
        mailSender.send(message);
    }
}
