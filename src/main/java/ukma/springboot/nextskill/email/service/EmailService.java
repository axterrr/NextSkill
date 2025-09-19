package ukma.springboot.nextskill.email.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

}
