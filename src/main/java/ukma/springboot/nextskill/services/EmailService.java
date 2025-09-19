package ukma.springboot.nextskill.services;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

}
